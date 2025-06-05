package panel;

import dto.FIleContent;
import service.CredentialsGeneratorService;

import javax.swing.*;
import java.awt.*;

public class PasswordCreationPanel extends JDialog {


    private final JTextField nameField = new JTextField(20);
    private final JTextField passwordField = new JTextField(20);
    private final JTextField loginField = new JTextField(20);
    private final JTextField websiteField = new JTextField(20);
    private final JTextField locationField = new JTextField(20);
    private final JTextField categoryField = new JTextField(20);

    private final JButton generateButton = new JButton("Generate Password");

    private FIleContent result;

    public PasswordCreationPanel(JFrame parent) {
        super(parent, "Enter Password Info", true);
        setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 5, 5)); // Отступы между ячейками

        fieldsPanel.add(new JLabel("Name:"));     fieldsPanel.add(nameField);
        fieldsPanel.add(new JLabel("Password:")); fieldsPanel.add(passwordField);
        fieldsPanel.add(new JLabel("Category:")); fieldsPanel.add(categoryField);
        fieldsPanel.add(new JLabel("Login:"));    fieldsPanel.add(loginField);
        fieldsPanel.add(new JLabel("Website:"));  fieldsPanel.add(websiteField);
        fieldsPanel.add(new JLabel("Location:")); fieldsPanel.add(locationField);

        add(fieldsPanel, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(generateButton);
        JButton saveButton = new JButton("Save");
        buttonsPanel.add(saveButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Логика кнопок
        generateButton.addActionListener(e -> {
            FIleContent generated = CredentialsGeneratorService.generateRandomCredentials();
            nameField.setText(generated.getName());
            passwordField.setText(generated.getPassword());
            loginField.setText(generated.getLogin());
            categoryField.setText(generated.getCategory());
            websiteField.setText(generated.getWebsite());
            locationField.setText(generated.getLocation());
        });

        saveButton.addActionListener(e -> {
            result = new FIleContent();
            result.setName(nameField.getText());
            result.setPassword(passwordField.getText());
            result.setLogin(loginField.getText());
            result.setWebsite(websiteField.getText());
            result.setLocation(locationField.getText());
            result.setCategory(categoryField.getText());
            dispose(); // закрываем окно
        });

        pack();
        setLocationRelativeTo(parent);
    }
    public FIleContent getResult() {
        return result;
    }

}
