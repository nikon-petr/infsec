package app.controller;

import helper.dsa.DsaHelper;
import helper.file.FileSaver;
import helper.xml.KeyType;
import helper.xml.KeyXmlReader;
import helper.xml.KeyXmlWriter;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    private static final String EMPTY_STRING = "";

    @FXML
    private Label generationSuccessLabel, importPrivateKeySuccessLabel, importPublicKeySuccessLabel, filePathLabel, loadSignPathLabel;

    @FXML
    private Label generationFailLabel, importPrivateKeyFailLabel, importPublicKeyFailLabel, fileOpenFailLabel, loadSignFailLabel;

    @FXML
    private VBox generateGroup, importGroup;

    @FXML
    RadioButton generateKeysRadioButton, importKeysRadioButton;

    @FXML
    private Button signFileButton, checkFileButton, exportPublicKeyButton, exportPrivateKeyButton;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @FXML
    private void handleGenerateKeysAction(ActionEvent e) {
        KeyPair keyPair = DsaHelper.generateKeyPair(1024);

        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();

        generationSuccessLabel.setVisible(true);
        generationFailLabel.setVisible(false);
    }

    @FXML
    private void onExportPublicKeyAction(ActionEvent e) {
        DirectoryChooser directoryChooser = helper.file.DirectoryChooser.get("Export to");
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            KeyXmlWriter keyXmlWriter = new KeyXmlWriter();
            String base64PublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            Document doc = keyXmlWriter.buildDocument(KeyType.PUBLIC, base64PublicKey);
            keyXmlWriter.saveToFile(doc, new File(selectedDirectory, "public-key.xml"));
        }
    }

    @FXML
    private void onExportPrivateKeyAction(ActionEvent e) {
        DirectoryChooser directoryChooser = helper.file.DirectoryChooser.get("Export to");
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            KeyXmlWriter keyXmlWriter = new KeyXmlWriter();
            String base64PrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            Document doc = keyXmlWriter.buildDocument(KeyType.PRIVATE, base64PrivateKey);
            keyXmlWriter.saveToFile(doc, new File(selectedDirectory, "private-key.xml"));
        }
    }

    @FXML
    private void handleImportPrivateKeyAction(ActionEvent e) {
        FileChooser fileChooser = helper.file.FileChooser.get("Open Private Key Xml", "Xml Files (*.xml)", "*.xml");
        File selectedFile = fileChooser.showOpenDialog(null);

        KeyXmlReader xmlReader = new KeyXmlReader();
        try {
            String privateKeyString = xmlReader.getKey(KeyType.PRIVATE, selectedFile);
            privateKey = xmlReader.buildPrivateKey(privateKeyString);

            importPrivateKeySuccessLabel.setVisible(true);
            importPrivateKeyFailLabel.setVisible(false);
        } catch (SAXException | InvalidKeySpecException e1) {
            importPrivateKeySuccessLabel.setVisible(false);
            importPrivateKeyFailLabel.setVisible(true);
        }
    }

    @FXML
    private void handleImportPublicKeyAction(ActionEvent e) {
        FileChooser fileChooser = helper.file.FileChooser.get("Open Public Key Xml", "Xml Files (*.xml)", "*.xml");
        File selectedFile = fileChooser.showOpenDialog(null);

        KeyXmlReader xmlReader = new KeyXmlReader();
        try {
            String publicKeyString = xmlReader.getKey(KeyType.PUBLIC, selectedFile);
            publicKey = xmlReader.buildPublicKey(publicKeyString);

            importPublicKeySuccessLabel.setVisible(true);
            importPublicKeyFailLabel.setVisible(false);
        } catch (SAXException | InvalidKeySpecException e1) {
            importPublicKeySuccessLabel.setVisible(false);
            importPublicKeyFailLabel.setVisible(true);
        }
    }

    @FXML
    private void onGenerateKeysRadioButton(ActionEvent e) {
        clearInput();
    }

    @FXML
    private void onImportKeysRadioButton(ActionEvent e) {
        clearInput();
    }

    @FXML
    private void onOpenFileAction(ActionEvent e) {
        FileChooser fileChooser = helper.file.FileChooser.get("Open Target File", "All Types (*)", "*.*");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            filePathLabel.setText(selectedFile.toString());
            filePathLabel.setVisible(true);
            fileOpenFailLabel.setVisible(false);
        }
    }

    @FXML
    private void onLoadSignAction(ActionEvent e) {
        FileChooser fileChooser = helper.file.FileChooser.get("Open Signature File", "All Types (*)", "*.*");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            loadSignPathLabel.setText(selectedFile.toString());
            loadSignPathLabel.setVisible(true);
            loadSignFailLabel.setVisible(false);
        }
    }

    @FXML
    private void onSignFileAction(ActionEvent e) {
        File inputFile = new File(filePathLabel.getText());
        FileChooser fileChooser = FileSaver.get("Save Sign File to", inputFile.getName() + ".dsa");
        File signFile = fileChooser.showSaveDialog(null);

        if (signFile != null) {
            try {
                DsaHelper.sign(inputFile, signFile, privateKey);
            } catch (InvalidKeyException e1) {
                e1.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Encryption complete");
            alert.setHeaderText(null);
            alert.setContentText("File was saved with name:\n" + signFile.getAbsolutePath());

            alert.showAndWait();
        }
    }

    @FXML
    private void onCheckFileAction(ActionEvent e) {
        File inputFile = new File(filePathLabel.getText());
        File signFile = new File(loadSignPathLabel.getText());

        if (inputFile != null && signFile != null) {
            boolean isVerified = false;

            try {
                isVerified = DsaHelper.verify(inputFile, signFile, publicKey);
            } catch (InvalidKeyException e1) {
                e1.printStackTrace();
            }

            String alertMsg = isVerified ? "Signature is valid" : "Signature is invalid";
            Alert.AlertType alertType = isVerified ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING;
            Alert alert = new Alert(alertType);
            alert.setTitle("Verifying complete");
            alert.setHeaderText(null);
            alert.setContentText(alertMsg);

            alert.showAndWait();
        }
    }

    @FXML
    private void onResetAction(ActionEvent e) {
        reset();
    }

    private void clearInput() {
        privateKey = null;
        publicKey = null;
        generationSuccessLabel.setVisible(false);
        importPrivateKeySuccessLabel.setVisible(false);
        importPublicKeySuccessLabel.setVisible(false);
        generationFailLabel.setVisible(false);
        importPrivateKeyFailLabel.setVisible(false);
        importPublicKeyFailLabel.setVisible(false);
    }

    private void reset() {
        clearInput();
        filePathLabel.setText(EMPTY_STRING);
        filePathLabel.setVisible(false);
        fileOpenFailLabel.setVisible(false);
        loadSignPathLabel.setText(EMPTY_STRING);
        loadSignPathLabel.setVisible(false);
        loadSignFailLabel.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generationSuccessLabel.managedProperty().bind(generationFailLabel.visibleProperty().not());
        importPrivateKeySuccessLabel.managedProperty().bind(importPrivateKeyFailLabel.visibleProperty().not());
        importPublicKeySuccessLabel.managedProperty().bind(importPublicKeyFailLabel.visibleProperty().not());
        loadSignPathLabel.managedProperty().bind(loadSignFailLabel.visibleProperty().not());

        filePathLabel.managedProperty().bind(fileOpenFailLabel.visibleProperty().not());

        BooleanBinding isKeyGenerated = generationSuccessLabel.visibleProperty().isEqualTo(new SimpleBooleanProperty(true));
        BooleanBinding isPrivateKeyImported = importPrivateKeySuccessLabel.visibleProperty().isEqualTo(new SimpleBooleanProperty(true));
        BooleanBinding isPublicKeyImported = importPublicKeySuccessLabel.visibleProperty().isEqualTo(new SimpleBooleanProperty(true));
        BooleanBinding isSignLoaded = loadSignPathLabel.visibleProperty().isEqualTo(new SimpleBooleanProperty(true));
        BooleanBinding isFileImported = filePathLabel.visibleProperty().isEqualTo(new SimpleBooleanProperty(true));

        checkFileButton.disableProperty().bind(isFileImported.and(isPublicKeyImported.or(isKeyGenerated)).and(isSignLoaded).not());
        signFileButton.disableProperty().bind(isFileImported.and(isPrivateKeyImported.or(isKeyGenerated)).not());

        generateGroup.disableProperty().bind(importKeysRadioButton.selectedProperty());
        importGroup.disableProperty().bind(generateKeysRadioButton.selectedProperty());

        exportPublicKeyButton.disableProperty().bind(generationSuccessLabel.visibleProperty().not());
        exportPrivateKeyButton.disableProperty().bind(generationSuccessLabel.visibleProperty().not());
    }
}
