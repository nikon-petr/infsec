package helper.rsa;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;

public class RsaHelper {

    public static KeyPair generateKeyPair(int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
            return keyPairGenerator.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeWithCipher(File inputFile, File outputFile, Cipher cipher){
        try (
                InputStream is = new FileInputStream(inputFile);
                CipherInputStream cip = new CipherInputStream(is, cipher);
                OutputStream os = new FileOutputStream(outputFile, false)
        ) {
            byte[] buffer = new byte[32768];

            while (true) {
                int readBytesCount = cip.read(buffer);
                if (readBytesCount == -1) {
                    break;
                }
                if (readBytesCount > 0) {
                    os.write(buffer, 0, readBytesCount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void encrypt(File inputFile, File outputFile, PublicKey publicKey){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            writeWithCipher(inputFile, outputFile, cipher);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public static void decrypt(File inputFile, File outputFile, PrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            writeWithCipher(inputFile, outputFile, cipher);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
