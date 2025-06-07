package Format;

import dto.PasswordFile;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Save implements Serializable {

    public Save(Map<File, PasswordFile> savingFormat) {
        this.savingFormat = savingFormat;
    }

    public  Save(){};

    private   Map<File, PasswordFile> savingFormat = new HashMap<>();


    public Map<File, PasswordFile> getSavingFormat() {
        return savingFormat;
    }

}
