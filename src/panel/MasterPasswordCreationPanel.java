package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class MasterPasswordCreationPanel extends JPanel {

    private final JPasswordField passwordField;
    private final JButton continueButton;

    public MasterPasswordCreationPanel(Consumer<String> onPasswordEntered) {
        this.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel("Enter your master password:");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        passwordField = new JPasswordField(20);
        continueButton = new JButton("Continue");

        // Центр с полем и кнопкой
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(passwordField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(continueButton);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(label, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

        // Кнопка "Продолжить"
        continueButton.addActionListener((ActionEvent e) -> {
            String password = new String(passwordField.getPassword());

            onPasswordEntered.accept(password);
        });
    }
}