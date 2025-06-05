package panel;

import dto.FIleContent;
import dto.PasswordFile;

import service.FileMapperService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class PasswordTablePanel extends JPanel {
    private JTable table;
    private PasswordTableModel tableModel;
    private JButton saveButton;
    private JButton addButton;
    private JButton deleteButton;


    private FileMapperService fileMapperService = new FileMapperService();
    private Map<File, PasswordFile> encryptedState;

    private PasswordFile editablePasswordFile = new PasswordFile();

    public PasswordTablePanel(PasswordFile passwordFile, Map<File, PasswordFile> map, File file) {
        this.encryptedState = map;
        setLayout(new BorderLayout());

        tableModel = new PasswordTableModel(passwordFile.getEntries());
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        saveButton = new JButton("Сохранить");
        addButton = new JButton("Добавить запись");
        deleteButton = new JButton("Удалить");

        saveButton.addActionListener(e -> saveButtonAction(file));
        addButton.addActionListener(e -> addButtonAction(file));
        deleteButton.addActionListener(e -> deleteSelectedRow(file));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel.add(saveButton);
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }


    public void saveButtonAction(File file) {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        editablePasswordFile.setEntries(tableModel.getEntries());
        editablePasswordFile.setIndex(null);
        fileMapperService.writeFileContentToFile(editablePasswordFile, file);

        JOptionPane.showMessageDialog(this, "Файл успешно сохранён!");
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

// 2. Переоткрываем окно с обновлённым PasswordFile
        PasswordFile refreshedPasswordFile = new PasswordFile(editablePasswordFile); // копия

// (Если у тебя есть логика расшифровки — можешь здесь её вызвать, если нужно)

        JFrame newTableFrame = new JFrame("Список паролей");
        newTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newTableFrame.setSize(800, 400);
        newTableFrame.setLocationRelativeTo(null);

        PasswordTablePanel newPanel = new PasswordTablePanel(refreshedPasswordFile, encryptedState, file);
        newTableFrame.setContentPane(newPanel);
        newTableFrame.setVisible(true);

    }

    private void deleteSelectedRow(File file) {
        int selectedRow = table.getSelectedRow();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить выбранную запись?",
                "Подтвердите удаление", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Удаление из модели
        tableModel.removeEntry(selectedRow);

        // Обновление модели и файла
        editablePasswordFile.setEntries(tableModel.getEntries());
        editablePasswordFile.setIndex(null);
        fileMapperService.writeFileContentToFile(editablePasswordFile, file);

        JOptionPane.showMessageDialog(this, "Запись успешно удалена!");
    }
}
