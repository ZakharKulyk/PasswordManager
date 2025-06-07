import db.FileDb;

import javax.swing.*;
import java.awt.*;

public class Demo {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Application(); // создаёт JFrame
        });
    }
}
