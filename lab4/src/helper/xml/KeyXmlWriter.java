package helper.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class KeyXmlWriter {

    private static final Map<KeyType, String> nodeNames;
    static
    {
        nodeNames = new HashMap<>();
        nodeNames.put(KeyType.PUBLIC, "public-key");
        nodeNames.put(KeyType.PRIVATE, "private-key");
    }

    public Document buildDocument(KeyType keyType, String key) {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(nodeNames.get(keyType));
        doc.appendChild(rootElement);

        rootElement.appendChild(doc.createTextNode(key));

        return doc;
    }

    public void saveToFile(Document doc, File file) {

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
