import panel.MainPanel;

import javax.swing.*;

public class Application extends JFrame {

    public Application(){

        this.setSize(1024, 768);
        this.setTitle("Hello, world!");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(new MainPanel());
    }
}
