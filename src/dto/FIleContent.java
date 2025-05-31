package dto;

import Format.Category;
import Format.Index;

public class FIleContent {

    private String Name;
    private String password;
    private String login;
    private String Website;
    private String Location;
    private Category category;
    private  Index index;

    public FIleContent(String name, String password, String login, String website, String location, Category category, Index index) {
        Name = name;
        this.password = password;
        this.login = login;
        this.Website = website;
        this.Location = location;
        this.category = category;
        this.index = index;
    }

    public FIleContent() {

    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
}
