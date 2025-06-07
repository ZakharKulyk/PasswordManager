package service;

import Format.Save;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveWriterService {
    public static void writeSave(Save save) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("history.txt"))) {
            objectOutputStream.writeObject(save);
        } catch (Exception e) {
            throw new RuntimeException("Error writing save file: " + e.getMessage(), e);
        }
    }
}
