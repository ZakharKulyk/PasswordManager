package dto;

import Format.Category;
import Format.Index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FIleContent implements Serializable {

    private String Name;
    private String password;
    private String login;
    private String Website;
    private String Location;
    private String category;
    private final List<String> decryptedAttempts = new ArrayList<>();


    public FIleContent(String name, String password, String login, String website, String location, String category) {
        Name = name;
        this.password = password;
        this.login = login;
        Website = website;
        Location = location;
        this.category = category;

    }

    public FIleContent(FIleContent original) {
        this.Name = original.Name;
        this.password = original.password;
        this.login = original.login;
        this.Website = original.Website;
        this.Location = original.Location;
        this.category = original.category;

    }

    public void addDecryptedAttempt(String attempt) {
        decryptedAttempts.add(attempt);
    }



    public FIleContent() {

    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @Override
    public String toString() {
        return "FIleContent{" +
                "Name='" + Name + '\'' +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", Website='" + Website + '\'' +
                ", Location='" + Location + '\'' +
                ", category='" + category + '\'' +
                ", decryptedAttempts=" + decryptedAttempts +
                '}';
    }
}
