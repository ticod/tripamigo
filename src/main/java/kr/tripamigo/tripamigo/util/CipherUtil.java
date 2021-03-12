package kr.tripamigo.tripamigo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class CipherUtil {

    private static final int SALT_SIZE = 16;

    private String byteToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(Integer.toString((b & 0xFF) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return byteToHexString(salt);
    }

    public String hashEncoding(String msg, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(EncodingType.HASH.getValue());
        String target = msg + salt;
        md.update(target.getBytes());
        return byteToHexString(md.digest());
    }

}
