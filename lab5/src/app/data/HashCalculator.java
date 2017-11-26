package app.data;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCalculator {

    public static byte[] calculateHash(String inputString) {
        byte[] hashBytes = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(inputString.getBytes());
            hashBytes = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashBytes;
    }

    public static String calculateHashString(String inputString) {
        byte[] bytes = calculateHash(inputString);
        return DatatypeConverter.printHexBinary(bytes).toUpperCase();
    }

}
