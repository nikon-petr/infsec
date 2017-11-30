package app.data;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Crypter {

    public static InputStream encrypt(InputStream data, byte[] passwordHash) throws InvalidKeyException{
        return crypt(data, passwordHash, Cipher.ENCRYPT_MODE);
    }

    public static InputStream decrypt(InputStream data, byte[] passwordHash) throws InvalidKeyException{
        return crypt(data, passwordHash, Cipher.DECRYPT_MODE);
    }

    private static InputStream crypt(InputStream data, byte[] key, int mode) throws InvalidKeyException {
        Cipher cipher = null;
        Key secretKeySpec = new SecretKeySpec(key, "AES");

        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        cipher.init(mode, secretKeySpec);

        return new CipherInputStream(data, cipher);
    }
}
