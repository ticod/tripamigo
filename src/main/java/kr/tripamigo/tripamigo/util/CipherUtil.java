package kr.tripamigo.tripamigo.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class CipherUtil {

    private static final int SALT_SIZE = 16;

    private static String byteToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(Integer.toString((b & 0xFF) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return byteToHexString(salt);
    }

    public static String hashEncoding(String msg, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(EncodingType.HASH.getValue());
        String target = msg + salt;
        md.update(target.getBytes());
        return byteToHexString(md.digest());
    }

}
