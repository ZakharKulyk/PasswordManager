package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class MasterPasswordRequestPanel extends JPanel {

    private final JPasswordField passwordField;
    private final JButton continueButton;

    public MasterPasswordRequestPanel(Consumer<String> onPasswordEntered) {
        this.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel("Enter MasterPassword for file decryption:");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        passwordField = new JPasswordField(20);
        continueButton = new JButton("continue");


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(passwordField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(continueButton);

        this.add(label, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);


        continueButton.addActionListener((ActionEvent e) -> {
            String password = new String(passwordField.getPassword());
            onPasswordEntered.accept(password);
        });
    }
}