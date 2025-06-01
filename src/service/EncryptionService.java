package service;

import dto.FIleContent;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EncryptionService {
    private static int getShiftFromMasterPassword(String masterPassword) {
        int shift = 0;
        for (char c : masterPassword.toCharArray()) {
            shift += c;
        }
        return shift;
    }

    public static FIleContent encrypt(FIleContent fileContent, String masterPassword) {
        int shift = getShiftFromMasterPassword(masterPassword);
        if (fileContent.getName() != null) {
            fileContent.setName(applyCaesarCipher(fileContent.getName(), shift));
        }
        if( fileContent.getPassword() != null) {
            fileContent.setPassword(applyCaesarCipher(fileContent.getPassword(), shift));
        }
        if (fileContent.getCategory() != null) {
            fileContent.setCategory(applyCaesarCipher(fileContent.getCategory(), shift));
        }

        if (fileContent.getLogin() != null) {
            fileContent.setLogin(applyCaesarCipher(fileContent.getLogin(), shift));
        }

        if (fileContent.getWebsite() != null) {
            fileContent.setWebsite(applyCaesarCipher(fileContent.getWebsite(), shift));
        }

        if (fileContent.getLocation() != null) {
            fileContent.setLocation(applyCaesarCipher(fileContent.getLocation(), shift));
        }

        return fileContent;
    }

    public static FIleContent decrypt(FIleContent fileContent, String masterPassword) {
        int shift = getShiftFromMasterPassword(masterPassword);

       if (fileContent.getName() != null) {
            fileContent.setName(applyCaesarCipher(fileContent.getName(), -shift));
        }
        if (fileContent.getPassword() != null) {
            fileContent.setPassword(applyCaesarCipher(fileContent.getPassword(), -shift));
        }
        if (fileContent.getCategory() != null) {
            fileContent.setCategory(applyCaesarCipher(fileContent.getCategory(), -shift));
        }
        if (fileContent.getLogin() != null) {
            fileContent.setLogin(applyCaesarCipher(fileContent.getLogin(), -shift));
        }
        if (fileContent.getWebsite() != null) {
            fileContent.setWebsite(applyCaesarCipher(fileContent.getWebsite(), -shift));
        }
        if (fileContent.getLocation() != null) {
            fileContent.setLocation(applyCaesarCipher(fileContent.getLocation(), -shift));
        }

        return fileContent;
    }

    private static String applyCaesarCipher(String input, int shift) {
        if (input == null) return "";

        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] += shift;
        }
        return new String(chars);
    }
}

