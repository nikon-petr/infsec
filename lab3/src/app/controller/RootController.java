package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RootController {

    private static final String EMPTY_STRING = "";

    @FXML
    private Label generationStatusLabel, importPrivateKeyStatusLabel, importPublicKeyStatusLabel;

    @FXML
    private VBox generateGroup, importGroup;

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
}
