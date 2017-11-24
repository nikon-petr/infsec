package app.controller;

import app.helper.FileDialogHelper;
import app.model.EncryptorModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class EncryptorController implements Initializable {

    private EncryptorModel encryptorModel;

    @FXML
    private ImageView sourceImageView, resultImageView;

    @FXML
    private Button openInputFileButton, encryptButton, saveResultImageButton;

    private final Image placeholderImage = new Image(getClass().getResourceAsStream("/images/placeholder.png"));

    @FXML
    private Label inputFilePathLabel;

    @FXML
    private CheckBox isUsingPasswordCheckBox;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onOpenImageAction(ActionEvent event){
        File inputImageFile = FileDialogHelper.chooseFile("Open Input Image", FileDialogHelper.ExtensionFilters.BMP);
        if (inputImageFile != null) {
            try {
                encryptorModel.setInputImage(new Image(new FileInputStream(inputImageFile)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onOpenInputFileAction(ActionEvent event){
        File inputFile = FileDialogHelper.chooseFile("Open Input File", FileDialogHelper.ExtensionFilters.ANY);
        encryptorModel.setInputFile(inputFile);
        inputFilePathLabel.setText(inputFile.getPath());
    }

    @FXML
    private void onEncryptAction(ActionEvent event){

    }

    @FXML
    private void onSaveAsAction(ActionEvent event){

    }

    @FXML
    private void onResetAction(ActionEvent event){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        encryptorModel = new EncryptorModel();

        encryptorModel.inputImageProperty().set(placeholderImage);
        encryptorModel.outputImageProperty().set(placeholderImage);

        inputFilePathLabel.visibleProperty().bind(inputFilePathLabel.textProperty().isNotEmpty());
        passwordField.disableProperty().bind(isUsingPasswordCheckBox.selectedProperty().not());
        sourceImageView.imageProperty().bind(encryptorModel.inputImageProperty());
        resultImageView.imageProperty().bind(encryptorModel.outputImageProperty());


        encryptButton.disableProperty().bind(
                encryptorModel.inputFileProperty().isNull()
                        .or(encryptorModel.isUsingPasswordProperty().and(encryptorModel.passwordProperty().isEmpty()))
                        .or(encryptorModel.inputImageProperty().isNull().or(encryptorModel.inputImageProperty().isEqualTo(placeholderImage)))
        );

        encryptorModel.passwordProperty().bind(passwordField.textProperty());
        encryptorModel.isUsingPasswordProperty().bind(isUsingPasswordCheckBox.selectedProperty());
    }
}
