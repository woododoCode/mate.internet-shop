package dev.internetshop.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashUtil {
    private static final int ITERATION_COUNT = 10000;
    private static final int KEY_LENGTH = 512;

    public static String hashPassword(String password, byte[] salt) {
        try {
            char[] passwordToChar = password.toCharArray();
            SecretKeyFactory secretKeyFactory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(passwordToChar, salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKey secretKey = secretKeyFactory.generateSecret(spec);
            byte[] keyEncoded = secretKey.getEncoded();
            StringBuilder hashedString = new StringBuilder();
            for (byte b : keyEncoded) {
                hashedString.append(String.format("%02x", b));
            }
            return hashedString.toString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
