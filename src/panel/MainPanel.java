package panel;

import Format.Index;
import db.FileDb;
import dto.FIleContent;
import dto.PasswordFile;
import exeption.WrongFileFormat;
import service.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainPanel extends JPanel {



    private final FileMapperService fileMapperService = new FileMapperService();
    private JButton createButton; // for creating files
    private JButton openButton;   // for opening existing files
    private Map<File, PasswordFile> encryptedState = new HashMap<>();
    private  FileDb db = FileDb.getInstance();



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

        db.setSave(SaveReaderService.readSave());
        if(db.getSave() != null) {
            encryptedState = db.getSave().getSavingFormat();
        }
    }


    private void openFileAct(ActionEvent e) {
        PasswordFile passwordFile = new PasswordFile();
        JFileChooser fileChooser = new JFileChooser();


        FileNameExtensionFilter filter = new FileNameExtensionFilter("Password files (*.zk)", "zk");
        fileChooser.setFileFilter(filter);

        fileChooser.setDialogTitle("open a file with passwords");

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Получаем выбранный файл
            File selectedFile = fileChooser.getSelectedFile();

            JOptionPane.showMessageDialog(this,
                    "File chosen: " + selectedFile.getAbsolutePath());

            try {
                fileMapperService.mapToFileContent(selectedFile, passwordFile);

                if (passwordFile.getIndex() == null || passwordFile.getIndex().getIndexName().isEmpty()) {
                    passwordFile.setIndex(new Index(IndexGenerator.generateIndex()));
                    JFrame dialogFrame = new JFrame("Создание мастер-пароля");
                    dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    dialogFrame.setSize(400, 150);

                    MasterPasswordCreationPanel panel = new MasterPasswordCreationPanel(masterPassword -> {
                        System.out.println("Пользователь ввёл мастер-пароль: " + masterPassword);


                        for (FIleContent entry : passwordFile.getEntries()) {
                            EncryptionService.encrypt(entry, masterPassword);
                        }

                        encryptedState.put(selectedFile, passwordFile);

                        fileMapperService.writeFileContentToFile(passwordFile, selectedFile);

                        // закрываем окно
                        dialogFrame.dispose();
                    });

                    dialogFrame.setContentPane(panel);
                    dialogFrame.setLocationRelativeTo(null); // центр экрана
                    dialogFrame.setVisible(true);
                } else if (passwordFile.getIndex() != null && !(passwordFile.getIndex().getIndexName().isEmpty())) {
                    JFrame requestFrame = new JFrame("Ввод мастер-пароля");
                    requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    requestFrame.setSize(400, 150);

                    MasterPasswordRequestPanel requestPanel = new MasterPasswordRequestPanel(enteredPassword -> {
                        System.out.println("Введённый пароль: " + enteredPassword);

                        encryptedState.get(selectedFile).addDecryptedAttempt(GetLastDecryptionAttemptService.getLastDecryptionAttempt());

                        PasswordFile passwordFileCopyOfState = new PasswordFile(encryptedState.get(selectedFile));

                        for (FIleContent entry : passwordFileCopyOfState.getEntries()) {
                            EncryptionService.decrypt(entry, enteredPassword);
                        }

                        fileMapperService.writeFileContentToFile(passwordFileCopyOfState, selectedFile);

                        requestFrame.dispose();

                        JFrame tableFrame = new JFrame("Список паролей");
                        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        tableFrame.setSize(800, 400);
                        tableFrame.setLocationRelativeTo(null);


                        PasswordTablePanel tablePanel = new PasswordTablePanel(passwordFileCopyOfState,encryptedState,selectedFile);
                        tableFrame.setContentPane(tablePanel);

                        tableFrame.setVisible(true);

                    });

                    requestFrame.setContentPane(requestPanel);
                    requestFrame.setLocationRelativeTo(null);
                    requestFrame.setVisible(true);
                }

            } catch (WrongFileFormat ex) {
                ex.printStackTrace();
                System.out.println("Invalid file format: " + ex.getMessage());
            }

        }
    }


    private void createFileAct(ActionEvent e) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Password files (*.zk)", "zk");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);


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

            if (newFile.exists()) {
                JOptionPane.showMessageDialog(this,
                        "File already exists. Please choose a different name.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Создаем новый файл
            try {
                if (newFile.createNewFile()) {
                    System.out.println("File created: " + newFile.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException i) {
                System.out.println("An error occurred.");
                i.printStackTrace();
            }

            PasswordCreationPanel passwordCreationPanel = new PasswordCreationPanel((JFrame) SwingUtilities.getWindowAncestor(this));
            passwordCreationPanel.setVisible(true);

            PasswordFile passwordFile = new PasswordFile();

            FIleContent newContent = passwordCreationPanel.getResult();

            if (newContent == null) return; // in case if user pressed cancel

            passwordFile.addEntry(newContent);

            passwordFile.setIndex(new Index(IndexGenerator.generateIndex()));

            JFrame dialogFrame = new JFrame("Создание мастер-пароля");
            dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dialogFrame.setSize(400, 150);

            MasterPasswordCreationPanel panel = new MasterPasswordCreationPanel(masterPassword -> {
                System.out.println("Пользователь ввёл мастер-пароль: " + masterPassword);


                for (FIleContent entry : passwordFile.getEntries()) {
                    EncryptionService.encrypt(entry, masterPassword);
                }

                encryptedState.put(newFile, passwordFile);

                fileMapperService.writeFileContentToFile(passwordFile, newFile);

                // закрываем окно
                dialogFrame.dispose();
            });

            dialogFrame.setContentPane(panel);
            dialogFrame.setLocationRelativeTo(null); // центр экрана
            dialogFrame.setVisible(true);


        }

    }

    public Map<File, PasswordFile> getEncryptedState() {
        return encryptedState;
    }
}
