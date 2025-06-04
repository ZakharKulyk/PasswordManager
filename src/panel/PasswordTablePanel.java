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

        saveButton.addActionListener(e->saveButtonAction(encryptedState, file));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel.add(saveButton);


        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void saveButtonAction (Map<File, PasswordFile>map, File file) {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        editablePasswordFile.setEntries(tableModel.getEntries());
        editablePasswordFile.setIndex(null);
        fileMapperService.writeFileContentToFile(editablePasswordFile, file);

        JOptionPane.showMessageDialog(this, "Файл успешно сохранён!");
    }
}
