import javax.swing.*;

public class Demo {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Application(); // создаёт JFrame
        });
    }
}
