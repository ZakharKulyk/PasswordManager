package panel;

import dto.FIleContent;

import javax.swing.*;
import java.awt.*;

public class PasswordCreationPanel  extends JDialog {

    private final JTextField nameField = new JTextField(20);
    private final JTextField passwordField = new JTextField(20);
    private final JTextField loginField = new JTextField(20);
    private final JTextField websiteField = new JTextField(20);
    private final JTextField locationField = new JTextField(20);
    private final JTextField categoryField = new JTextField(20);

    private FIleContent result;

    public PasswordCreationPanel(JFrame parent) {
        super(parent, "Enter Password Info", true);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Password:")); add(passwordField);
        add(new JLabel("Category:")); add(categoryField);
        add(new JLabel("Login:")); add(loginField);
        add(new JLabel("Website:")); add(websiteField);
        add(new JLabel("Location:")); add(locationField);

        JButton saveButton = new JButton("Save");
        add(new JLabel()); // пустой для выравнивания
        add(saveButton);

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
