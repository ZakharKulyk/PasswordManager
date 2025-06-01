package service;

import java.util.Random;

public class IndexGenerator {

    private static final Random random = new Random();

    public static String generateIndex() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            sb.append(letter);
        }


        for (int i = 0; i < 2; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }
}
