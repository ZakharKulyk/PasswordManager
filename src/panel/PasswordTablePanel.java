package panel;

import dto.FIleContent;
import dto.PasswordFile;

import service.FileMapperService;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PasswordTablePanel extends JPanel {
    private JTable table;
    private PasswordTableModel tableModel;
    private JButton saveButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton searchByNameButton;
    private JTextField searchField;
    private JTextField categoryField;
    private JButton sortButton;
    private JButton deleteCategoryButton;
    private TableRowSorter<PasswordTableModel> sorter;


    private FileMapperService fileMapperService = new FileMapperService();


    private PasswordFile editablePasswordFile = new PasswordFile();

    public PasswordTablePanel(PasswordFile passwordFile, File file) {

        setLayout(new BorderLayout());

        tableModel = new PasswordTableModel(passwordFile.getEntries());
        table = new JTable(tableModel);

        sorter = new TableRowSorter<>(tableModel);

        table.setRowSorter(sorter);

        add(new JScrollPane(table), BorderLayout.CENTER);

        saveButton = new JButton("Save");
        addButton = new JButton("Add Record");
        deleteButton = new JButton("Delete Record");
        searchByNameButton = new JButton("search by name");
        sortButton = new JButton("Sort By Name And Category");
        searchField = new JTextField("Find By Name", 20);
        deleteCategoryButton = new JButton("Delete Category");
        categoryField = new JTextField("Delete Category", 20);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));


        searchPanel.add(searchField);
        searchPanel.add(searchByNameButton);

        categoryPanel.add(categoryField);
        categoryPanel.add(deleteCategoryButton);

        saveButton.addActionListener(e -> saveButtonAction(file));
        addButton.addActionListener(e -> addButtonAction(file));
        deleteButton.addActionListener(e -> deleteSelectedRow(file));

        sortButton.addActionListener(e -> sortByNameAndCategory());

        searchByNameButton.addActionListener(e -> {

            String targetName = searchField.getText().trim();
            int columnIndex = 0;

            List<Integer> rowindices = new ArrayList<>();
            for (int i = 0; i < table.getRowCount(); i++) {
                String valueAt = table.getValueAt(i, columnIndex).toString();
                if (valueAt.equalsIgnoreCase(targetName)) {
                    rowindices.add(i);
                }
            }
            if (!rowindices.isEmpty()) {
                table.clearSelection();
                for (Integer rowindex : rowindices) {
                    table.addRowSelectionInterval(rowindex, rowindex);
                }
                table.scrollRectToVisible(table.getCellRect(rowindices.get(0), 0, true));

            } else {
                JOptionPane.showMessageDialog(this, "No record found with name: " + targetName);
            }

        });

        deleteCategoryButton.addActionListener(e -> {
            String targetCategory = categoryField.getText().trim();

            if (targetCategory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "enter category to be deleted!");
                return;
            }

            List<FIleContent> updatedEntries = tableModel.getEntries().stream()
                    .filter(entry -> {
                        String cat = entry.getCategory();
                        return cat == null || !cat.equalsIgnoreCase(targetCategory);
                    })
                    .collect(Collectors.toList());

            if (updatedEntries.size() == tableModel.getEntries().size()) {
                JOptionPane.showMessageDialog(this, "that category does not exist!");
                return;
            }

            editablePasswordFile.setEntries(updatedEntries);
            editablePasswordFile.setIndex(null);
            fileMapperService.writeFileContentToFile(editablePasswordFile, file);

            JOptionPane.showMessageDialog(this, "Category '" + targetCategory + "was deleted!");


            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }


            PasswordFile refreshedPasswordFile = new PasswordFile(editablePasswordFile);

            JFrame newTableFrame = new JFrame("Password List");
            newTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newTableFrame.setSize(800, 400);
            newTableFrame.setLocationRelativeTo(null);

            PasswordTablePanel newPanel = new PasswordTablePanel(refreshedPasswordFile,  file);
            newTableFrame.setContentPane(newPanel);
            newTableFrame.setVisible(true);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel.add(saveButton);
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(sortButton);


        add(bottomPanel, BorderLayout.SOUTH);
        add(searchPanel, BorderLayout.NORTH);
        add(categoryPanel, BorderLayout.WEST);
    }


    public void saveButtonAction(File file) {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        editablePasswordFile.setEntries(tableModel.getEntries());
        editablePasswordFile.setIndex(null);
        fileMapperService.writeFileContentToFile(editablePasswordFile, file);

        JOptionPane.showMessageDialog(this, "File successfully saved!");
    }

    public void addButtonAction(File file) {
        PasswordCreationPanel passwordCreationPanel = new PasswordCreationPanel((JFrame) SwingUtilities.getWindowAncestor(this));
        passwordCreationPanel.setVisible(true);


        editablePasswordFile.setEntries(tableModel.getEntries());
        FIleContent newRecord = passwordCreationPanel.getResult();
        editablePasswordFile.addEntry(newRecord);
        fileMapperService.writeFileContentToFile(editablePasswordFile, file);

        JOptionPane.showMessageDialog(this, "record was added successfully!");
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }


        PasswordFile refreshedPasswordFile = new PasswordFile(editablePasswordFile);


        JFrame newTableFrame = new JFrame("Password List");
        newTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newTableFrame.setSize(800, 400);
        newTableFrame.setLocationRelativeTo(null);

        PasswordTablePanel newPanel = new PasswordTablePanel(refreshedPasswordFile,  file);
        newTableFrame.setContentPane(newPanel);
        newTableFrame.setVisible(true);

    }

    private void sortByNameAndCategory() {

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private void deleteSelectedRow(File file) {
        int selectedRow = table.getSelectedRow();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure that you want to delete the record?",
                "Confirm deletion", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }


        tableModel.removeEntry(selectedRow);


        editablePasswordFile.setEntries(tableModel.getEntries());
        editablePasswordFile.setIndex(null);
        fileMapperService.writeFileContentToFile(editablePasswordFile, file);

        JOptionPane.showMessageDialog(this, "Record was deleted!");
    }
}
