package mate.academy.internetshop.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class HashUtil {
    private static final int ITERATION_COUNT = 10000;
    public static final int KEY_LENGTH = 512;

    public static String hashPassword(String password, byte[] salt) {
        try {
            char[] psw = password.toCharArray();
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( psw, salt, ITERATION_COUNT, KEY_LENGTH );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            StringBuilder hashedString = new StringBuilder();
            for (byte b : res) {
                hashedString.append(String.format("%02x", b));
            }
            return hashedString.toString();
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }

    public static byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "SALTSALTt";
        byte[] salt = s.getBytes();
        String password = "pass";
        System.out.println(HashUtil.hashPassword(password, salt));
    }
}
