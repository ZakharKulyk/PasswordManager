package db;

import Format.Save;

import java.io.*;
import java.util.List;
import java.util.Queue;

public class FileDb  {


    private  static FileDb instance;
    private Save save;
    
    private FileDb(){

    }
    
    public static  FileDb getInstance() {
        if(instance == null) {
            instance = new FileDb();
        }
        return instance;
    }

    public Save getSave() {
        return save;
    }

    public void setSave(Save save) {
        this.save = save;
    }
}
