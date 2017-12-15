package app.data;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashCalculator {

    private HashCalculator() {}

    public static byte[] calculateHash(String inputString) {
        byte[] hashBytes = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(inputString.getBytes("UTF-8"));
            hashBytes = messageDigest.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return hashBytes;
    }

    public static String calculateHashString(String inputString) {
        byte[] bytes = calculateHash(inputString);
        return DatatypeConverter.printHexBinary(bytes).toUpperCase();
    }

}
