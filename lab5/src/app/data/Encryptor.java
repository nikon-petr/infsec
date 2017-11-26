package app.data;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    public static InputStream encrypt(InputStream data, byte[] passwordHash) throws InvalidKeyException {
        Cipher cipher = null;
        Key secretKeySpec = new SecretKeySpec(passwordHash, "AES");

        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        return new CipherInputStream(data, cipher);
    }
}
