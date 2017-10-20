package app;

import app.model.CryptoMethod;
import app.model.CryptoMethods;
import app.model.CryptoModes;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Test {

    public static void main(String[] args) {
        CryptoMethod cm = new CryptoMethod(CryptoMethods.DES, CryptoModes.CBC);
        File f1 = new File("/home/dobavka/IS1/original.txt");
        cm.encode(f1);
        File f2 = new File("/home/dobavka/IS1/original.txt.encoded.des.cbc");
        cm.decode(f2);

        System.out.println(cm.getEncodeTime());
        System.out.println(cm.getDecodeTime());

        /*try {
            String str = "ABfff";

            Key key = KeyGenerator.getInstance("DESede").generateKey();

            Cipher ecipher = Cipher.getInstance("DESede/CCM/PKCS5Padding");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
//            byte[] iv = ecipher.getIV();

            byte[] utf8e = str.getBytes("UTF8");

            byte[] enc = ecipher.doFinal(utf8e);

            String encString = new BASE64Encoder().encode(enc);



            Cipher dcipher = Cipher.getInstance("DESede/CCM/PKCS5Padding");
            dcipher.init(Cipher.DECRYPT_MODE, key*//*, new IvParameterSpec(iv)*//*);

            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(encString);

            byte[] utf8d = dcipher.doFinal(dec);

            System.out.println(new String(utf8d, "UTF8"));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException  e) {
            e.printStackTrace();
        } *//*catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }*/
    }
}
