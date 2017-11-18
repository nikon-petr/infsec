package app.controller;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable{

    private static final String EMPTY_STRING = "";

    @FXML
    private Label generationStatusLabel, importPrivateKeyStatusLabel, importPublicKeyStatusLabel, filePathLabel;

    @FXML
    private VBox generateGroup, importGroup;

    @FXML
    private ChoiceBox modeChoiceBox;

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
        generateGroup.setDisable(false);
        importGroup.setDisable(true);
    }

    @FXML
    private void onImportKeysRadioButton(ActionEvent e){
        privateKey = EMPTY_STRING;
        publicKey = EMPTY_STRING;
        clearInput();
        generateGroup.setDisable(true);
        importGroup.setDisable(false);
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

        BooleanBinding isKeyGenerated = generationStatusLabel.textProperty().isEmpty();
        BooleanBinding isKeyImported = importPrivateKeyStatusLabel.textProperty().isEmpty()
                .or(importPublicKeyStatusLabel.textProperty().isEmpty());
        BooleanBinding isFileImported = filePathLabel.textProperty().isEmpty();

        BooleanBinding isStartEnabled = isKeyGenerated.and(isKeyImported).or(isFileImported);

    }
}
