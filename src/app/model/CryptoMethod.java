package app.model;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class CryptoMethod {

    private String transformation;

    private String method;
    private String mode;

    private Cipher ecipher;
    private Cipher dcipher;
    private byte[] iv;

    private long encodeTime;
    private long decodeTime;

    public CryptoMethod(CryptoMethods method, CryptoModes mode) {

        this.method = method.toString();
        this.mode = mode.toString();

        transformation = String.format("%s/%s/PKCS5Padding", method, mode);

        try {
            Key key = KeyGenerator.getInstance(method.toString()).generateKey();

            ecipher = Cipher.getInstance(transformation);
            ecipher.init(Cipher.ENCRYPT_MODE, key);

            if (mode != CryptoModes.ECB) {
                iv = ecipher.getIV();
            }

            dcipher = Cipher.getInstance(transformation);

            if (mode != CryptoModes.ECB) {
                dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            } else {
                dcipher.init(Cipher.DECRYPT_MODE, key);
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    public File encode(File inputFile) {
        String absolutePath = inputFile.getAbsolutePath();
        String dirPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
        File outputFile = new File(dirPath, String.format("%s.%s.%s.%s", inputFile.getName(), "encoded", method.toLowerCase(), mode.toLowerCase()));
        encodeTime = System.nanoTime();
        code(inputFile, outputFile, ecipher);
        encodeTime = (System.nanoTime() - encodeTime) / 1000000;
        return outputFile;
    }

    public File decode(File inputFile) {
        String absolutePath = inputFile.getAbsolutePath();
        String dirPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
        File outputFile = new File(dirPath, String.format("%s.%s.%s.%s", inputFile.getName(), "decoded", method.toLowerCase(), mode.toLowerCase()));
        decodeTime = System.nanoTime();
        code(inputFile, outputFile, dcipher);
        decodeTime = (System.nanoTime() - decodeTime) / 1000000;
        return outputFile;
    }

    private void code(File inputFile, File outputFile, Cipher cipher) {
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

    public long getEncodeTime() {
        return encodeTime;
    }

    public long getDecodeTime() {
        return decodeTime;
    }
}
