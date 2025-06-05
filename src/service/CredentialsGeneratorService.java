package service;

import dto.FIleContent;

import java.util.List;
import java.util.Random;

public class CredentialsGeneratorService {
    private final static List<String> categories = List.of("Email", "Social", "Finance", "Work", "Entertainment", "Shopping");
    private final static List<String> websites = List.of("example.com", "example.org", "example.net", "example.edu", "example.io");
    private final static List<String> locations = List.of("Home", "Work", "School", "Gym", "Cafe");
    private final static List<String> logins = List.of("user", "admin", "guest", "member", "owner");
    private final static List<String> names = List.of("John Doe", "Jane Smith", "Alice Johnson", "Bob Brown", "Charlie White");

    private static final Random random = new Random();

    public static FIleContent generateRandomCredentials() {
        String name = names.get(random.nextInt(names.size()));
        String password = generateRandomPassword(12);
        String login = logins.get(random.nextInt(logins.size()));
        String website = websites.get(random.nextInt(websites.size()));
        String location = locations.get(random.nextInt(locations.size()));
        String category = categories.get(random.nextInt(categories.size()));

        return new FIleContent(name, password, login, website, location, category);
    }

    private static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

}
