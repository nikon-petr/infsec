package helper.dsa;

import java.io.*;
import java.security.*;

public class DsaHelper {
    public static KeyPair generateKeyPair(int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyPairGenerator.initialize(keySize, random);
            return keyPairGenerator.genKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sign(File file, File sign, PrivateKey privateKey) throws InvalidKeyException {
        Signature dsa = null;
        try {
            dsa = Signature.getInstance("SHA1withDSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        dsa.initSign(privateKey);

        try(
                InputStream is = new FileInputStream(file);
                BufferedInputStream bin = new BufferedInputStream(is);
                OutputStream os = new FileOutputStream(sign, false)
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bin.read(buffer)) >= 0) {
                dsa.update(buffer, 0, len);
            }
            os.write(dsa.sign());
        } catch (SignatureException | IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean verify(File file, File sign, PublicKey publicKey) throws InvalidKeyException {
        try (
                InputStream ifs = new FileInputStream(file);
                BufferedInputStream bin = new BufferedInputStream(ifs);
                InputStream iss = new FileInputStream(sign)
        ) {
            byte[] sigToVerify = new byte[iss.available()];
            iss.read(sigToVerify);

            Signature sig = Signature.getInstance("SHA1withDSA");
            sig.initVerify(publicKey);

            byte[] buffer = new byte[1024];
            int len;
            while (bin.available() != 0) {
                len = bin.read(buffer);
                sig.update(buffer, 0, len);
            }

            return sig.verify(sigToVerify);

        } catch (IOException | NoSuchAlgorithmException | SignatureException e) {
            e.printStackTrace();
            return false;
        }
    }
}
