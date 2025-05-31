package panel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.File;

public class MainPanel extends JPanel {

    private JButton createButton; // for creating files
    private JButton openButton;   // for opening existing files



    public MainPanel() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new FlowLayout());

        createButton = new JButton("Create File");
        openButton = new JButton("Open File");

        this.add(createButton);
        this.add(openButton);

        createButton.addActionListener(this::createFileAct);
        openButton.addActionListener(this::openFileAct);

    }


    private void openFileAct(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Password files (*.zk)", "zk");
        fileChooser.setFileFilter(filter);

        fileChooser.setDialogTitle("open a file with passwords");

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Получаем выбранный файл
            File selectedFile = fileChooser.getSelectedFile();



            // Пока что просто показываем путь выбранного файла
            // В будущем сюда добавим логику для загрузки файла и запроса мастер-пароля
            JOptionPane.showMessageDialog(this,
                    "File chosen: " + selectedFile.getAbsolutePath());

        }
    }


    private void createFileAct(ActionEvent e){

        JFileChooser fileChooser = new JFileChooser();


        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Получаем указанный файл
            File newFile = fileChooser.getSelectedFile();

            // Пока что просто показываем путь нового файла
            // В будущем сюда добавим логику для создания файла и запроса мастер-пароля
            JOptionPane.showMessageDialog(this,
                    "Created new File: " + newFile.getAbsolutePath());
            System.out.println(newFile.getName());
            System.out.println(newFile.getAbsolutePath());
        }

    }



}
