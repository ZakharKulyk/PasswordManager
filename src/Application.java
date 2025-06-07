import Format.Save;
import panel.MainPanel;
import service.SaveWriterService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame {

    public Application(){

        this.setSize(1024, 768);
        this.setTitle("Hello, world!");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        MainPanel mainPanel = new MainPanel();

        add(mainPanel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               SaveWriterService.writeSave(new Save(mainPanel.getEncryptedState()));
               dispose();
            }

        });

    }

}
