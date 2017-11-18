package app.controller;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable{

    private static final String EMPTY_STRING = "";

    @FXML
    private Label generationStatusLabel, importPrivateKeyStatusLabel, importPublicKeyStatusLabel, filePathLabel;

    @FXML
    private VBox generateGroup, importGroup;

    @FXML RadioButton generateKeysRadioButton, importKeysRadioButton;

    @FXML
    private ChoiceBox modeChoiceBox;

    @FXML
    private Button startButton;

    private String privateKey, publicKey;

    @FXML
    private void handleGenerateKeysAction(ActionEvent e){
        generationStatusLabel.setText("successful");
    }

    @FXML
    private void handleImportPrivateKeyAction(ActionEvent e){
        importPrivateKeyStatusLabel.setText("successful");
    }

    @FXML
    private void handleImportPublicKeyAction(ActionEvent e){
        importPublicKeyStatusLabel.setText("successful");
    }

    @FXML
    private void onGenerateKeysRadioButton(ActionEvent e){
        clearInput();
    }

    @FXML
    private void onImportKeysRadioButton(ActionEvent e){
        privateKey = EMPTY_STRING;
        publicKey = EMPTY_STRING;
        clearInput();
    }

    @FXML
    private void onOpenFileAction(ActionEvent e){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text files (*txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File userDirectory = new File(System.getProperty("user.home"));

        fileChooser.setTitle("Text file");
        fileChooser.setInitialDirectory(userDirectory);
        File selectedDirectory = fileChooser.showOpenDialog(null);

        if (selectedDirectory != null) {
            filePathLabel.setText(selectedDirectory.toString());
        }
    }

    private void clearInput(){
        generationStatusLabel.setText("");
        importPrivateKeyStatusLabel.setText("");
        importPublicKeyStatusLabel.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modeChoiceBox.getItems().addAll("Encode", "Decode");
        modeChoiceBox.setValue("Encode");

        BooleanBinding isKeyGenerated = generationStatusLabel.textProperty().isNotEmpty();
        BooleanBinding isKeyImported = importPrivateKeyStatusLabel.textProperty().isNotEmpty()
                .and(importPublicKeyStatusLabel.textProperty().isNotEmpty());
        BooleanBinding isFileImported = filePathLabel.textProperty().isNotEmpty();

        BooleanBinding isStartEnabled = isKeyGenerated.and(isFileImported)
                .or(isKeyImported.and(isFileImported));

        startButton.disableProperty().bind(isStartEnabled.not());

        generateGroup.disableProperty().bind(importKeysRadioButton.selectedProperty());
        importGroup.disableProperty().bind(generateKeysRadioButton.selectedProperty());
    }
}
