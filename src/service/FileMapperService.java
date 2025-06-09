package service;


import Format.Index;
import dto.FIleContent;
import dto.PasswordFile;
import exeption.WrongFileFormat;

import java.io.*;

public class FileMapperService {

    public void mapToFileContent(File file, PasswordFile passwordFile) throws WrongFileFormat {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            String name = null;
            String password = null;
            String login = null;
            String website = null;
            String location = null;
            String category = null;
            String index = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {

                    if (name != null && password != null) {
                        FIleContent entry = new FIleContent(name, password, login, website, location, category);
                        passwordFile.addEntry(entry);

                        // Обнуляем переменные для следующей записи
                        name = password = login = website = location = category = null;
                    }
                    continue;
                }

                // Разбираем строку с форматом: key: value
                String[] parts = line.split(":", 2);
                if (parts.length < 2) {
                    throw new WrongFileFormat("Wrong line format: " + line);
                }
                String key = parts[0].toLowerCase().trim();
                String value = parts[1].trim();

                switch (key) {
                    case "index":
                        index = value;
                        passwordFile.setIndex(new Index(index));
                        break;
                    case "name":
                        name = value;
                        break;
                    case "password":
                        password = value;
                        break;
                    case "login":
                        login = value;
                        break;
                    case "website":
                        website = value;
                        break;
                    case "location":
                        location = value;
                        break;
                    case "category":
                        category = value;
                        break;
                    default:
                        break;
                }
            }


            if (name != null && password != null) {
                FIleContent entry = new FIleContent(name, password, login, website, location, category);
                passwordFile.addEntry(entry);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void writeFileContentToFile(PasswordFile passwordFile, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            // Записываем индекс файла
            if (passwordFile.getIndex() != null) {
                if (!passwordFile.getIndex().getIndexName().isEmpty()) {
                    bw.write("index: " + passwordFile.getIndex().getIndexName() + "\n\n");
                }
            }

            // Записываем каждый пароль
            for (FIleContent content : passwordFile.getEntries()) {
                if (content.getName() != null) {
                    if (!content.getName().isEmpty()) {
                        bw.write("name: " + content.getName() + "\n");
                    }
                }
                if (content.getPassword() != null) {
                    if (!content.getPassword().isEmpty()) {
                        bw.write("password: " + content.getPassword() + "\n");
                    }
                }
                if (content.getLogin() != null) {
                    if (!content.getLogin().isEmpty()) {
                        bw.write("login: " + content.getLogin() + "\n");
                    }
                }

                if (content.getWebsite() != null) {
                    if (!content.getWebsite().isEmpty()) {
                        bw.write("website: " + content.getWebsite() + "\n");
                    }
                }

                if (content.getLocation() != null) {
                    if (!content.getLocation().isEmpty()) {
                        bw.write("location: " + content.getLocation() + "\n");
                    }
                }

                if (content.getCategory() != null) {
                    if (!content.getCategory().isEmpty()) {
                        bw.write("category: " + content.getCategory() + "\n");
                    }
                }

                // Разделяем записи пустой строкой
                bw.write("\n");
            }


            if (!passwordFile.getDecryptedAttempts().isEmpty()) {
                bw.write("decryptedAttempts:\n");
                for (String attempt : passwordFile.getDecryptedAttempts()) {
                    bw.write(attempt + "\n");
                }
            }

            bw.flush();

        } catch (IOException e) {
            System.out.println("Error while writing file: " + file.getName());
            e.printStackTrace();
        }
    }
}
