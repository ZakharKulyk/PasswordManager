package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class GetLastDecryptionAttemptService {

    public static String getLastDecryptionAttempt() {
        StringBuilder lastAttempt = new StringBuilder();

        lastAttempt.append("Decryption Attempt Details:\n");
        lastAttempt.append("Date: "+ LocalDateTime.now());

        return  lastAttempt.toString();
    }
}
