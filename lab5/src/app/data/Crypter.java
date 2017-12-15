package app.data;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Crypter {

    public static CipherInputStream encrypt(InputStream data, byte[] passwordHash) {
        return encryptWithKey(data, passwordHash);
    }

    public static CipherOutputStream decrypt(OutputStream data, byte[] passwordHash) {
        return decryptWithKey(data, passwordHash);
    }

    private static CipherInputStream encryptWithKey(InputStream data, byte[] key) {
        Cipher cipher = null;
        Key secretKeySpec = new SecretKeySpec(Arrays.copyOf(key, 16), "AES");

        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return new CipherInputStream(data, cipher);
    }

    private static CipherOutputStream decryptWithKey(OutputStream data, byte[] key) {
        Cipher cipher = null;
        Key secretKeySpec = new SecretKeySpec(Arrays.copyOf(key, 16), "AES");

        try {
            cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return new CipherOutputStream(data, cipher);
    }
}
