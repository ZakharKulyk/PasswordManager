package dto;


import Format.Index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PasswordFile implements Serializable {

    private List<FIleContent> entries = new ArrayList<>();
    private Index index;
    private List<String> decryptedAttempts = new ArrayList<>();

    public PasswordFile(PasswordFile original) {
        this.index= original.index;
        this.decryptedAttempts = new ArrayList<>(original.decryptedAttempts);
        this.entries = new ArrayList<>();
        for (FIleContent entry : original.entries) {
            this.entries.add(new FIleContent(entry));
        }
    }

    public PasswordFile() {

    }


    public List<FIleContent> getEntries() {
        return entries;
    }

    public void setEntries(List<FIleContent> entries) {
        this.entries = entries;
    }

    public void addEntry(FIleContent content) {
        entries.add(content);
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public void setDecryptedAttempts(List<String> decryptedAttempts) {
        this.decryptedAttempts = decryptedAttempts;
    }

    public List<String> getDecryptedAttempts() {
        return decryptedAttempts;
    }

    public void addDecryptedAttempt(String attempt) {
        decryptedAttempts.add(attempt);
    }
}