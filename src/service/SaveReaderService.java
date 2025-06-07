package service;

import Format.Save;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SaveReaderService {
    public  static Save readSave(){

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("history.txt"))) {
            Object object = objectInputStream.readObject();
            return (Save) object;
        } catch (Exception e) {
            System.out.println("BASE FILE IS EMPTY");
            return null;
        }
    }
}
