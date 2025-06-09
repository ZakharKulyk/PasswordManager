package service;


import java.time.LocalDateTime;

public class GetLastDecryptionAttemptService {

    public static String getLastDecryptionAttempt() {
        StringBuilder lastAttempt = new StringBuilder();

        lastAttempt.append("Decryption Attempt Details:\n");
        lastAttempt.append("Date: "+ LocalDateTime.now());

        return  lastAttempt.toString();
    }
}
