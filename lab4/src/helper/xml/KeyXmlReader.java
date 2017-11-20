package helper.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class KeyXmlReader {
    private static final Map<KeyType, String> xsdUrl;
    static
    {
        xsdUrl = new HashMap<>();
        xsdUrl.put(KeyType.PUBLIC, "lab4/src/helper/xml/publicKeyXmlScheme.xsd");
        xsdUrl.put(KeyType.PRIVATE, "lab4/src/helper/xml/privateKeyXmlScheme.xsd");
    }

    public String getKey(KeyType keyType, File file) throws SAXException {
        try {
            File schemaFile = new File(xsdUrl.get(keyType));

            Source xmlFile = new StreamSource(file);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            return doc.getFirstChild().getTextContent();

        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PublicKey buildPublicKey(String publicKey) throws InvalidKeySpecException {
        try {
            byte[] data = Base64.getDecoder().decode((publicKey.getBytes()));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
            KeyFactory fact = KeyFactory.getInstance("DSA");
            return fact.generatePublic(spec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrivateKey buildPrivateKey(String publicKey) throws InvalidKeySpecException {
        try {
            byte[] data = Base64.getDecoder().decode((publicKey.getBytes()));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(data);
            KeyFactory fact = KeyFactory.getInstance("DSA");
            return fact.generatePrivate(spec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
