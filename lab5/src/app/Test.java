package app;

import app.data.Crypter;
import app.data.HashCalculator;

import java.io.*;
import java.security.InvalidKeyException;

public class Test {
    public static void main(String[] args) {
        byte[] password = HashCalculator.calculateHash("123");
        File inputFile = new File("/Users/nikon/Desktop/test.txt");
        File encryptedFile = new File("/Users/nikon/Desktop/encrypted.txt");
        File decryptedFile = new File("/Users/nikon/Desktop/decrypted.txt");

        try(
                InputStream is = new FileInputStream(inputFile);
                InputStream cis = Crypter.encrypt(is, password);
                OutputStream os = new FileOutputStream(encryptedFile)
        ) {
            int data = cis.read();
            int i = 0;
            while (data != -1){
                os.write(data);
                data = cis.read();

                System.out.println(i);
                i++;
            }
        } catch (IOException | InvalidKeyException e) {
            e.printStackTrace();
        }

        try(
                InputStream is = new FileInputStream(encryptedFile);
                OutputStream os = new FileOutputStream(decryptedFile);
                OutputStream cos = Crypter.decrypt(os, password)
        ) {
            int data = is.read();
            int i = 0;
            while (data != -1){
                cos.write(data);
                data = is.read();

                System.out.println(i);
                i++;
            }
        } catch (IOException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
