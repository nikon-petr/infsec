package app.controller;

import app.helper.AlertHelper;
import app.helper.FileDialogHelper;
import app.model.FileTooLongException;
import app.model.HiderModel;
import app.model.ImageTooSmallException;
import app.model.StringImageMaxInputFileSize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HiderController implements Initializable {

    private HiderModel hiderModel;

    @FXML
    private ImageView sourceImageView, resultImageView;

    @FXML
    private Button openInputFileButton, encryptButton, saveResultImageButton;

    @FXML
    private Label maxFileSizeLabel, inputFilePathLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onOpenImageAction(ActionEvent event) {
        File inputImageFile = FileDialogHelper.chooseFile("Open Input Image", FileDialogHelper.ExtensionFilters.PNG);
        if (inputImageFile != null) {
            try (InputStream inputFileStream = new FileInputStream(inputImageFile)) {
                hiderModel.setInputImage(new Image(inputFileStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onOpenInputFileAction(ActionEvent event) {
        File inputFile = FileDialogHelper.chooseFile("Open Input File", FileDialogHelper.ExtensionFilters.ANY);
        hiderModel.setInputFile(inputFile);
    }

    @FXML
    private void onEncryptAction(ActionEvent event) {
        try {
            hiderModel.hideData();
        } catch (FileTooLongException | ImageTooSmallException e) {
            AlertHelper.alert(Alert.AlertType.WARNING, "Warning", null, e.getMessage());
        } catch (FileNotFoundException e) {
            AlertHelper.alert(Alert.AlertType.ERROR, "Error", null, "File not found");
        }
    }

    @FXML
    private void onSaveAsAction(ActionEvent event) {
        File inputFile = FileDialogHelper.saveFile("Save result image", "result.png");
        hiderModel.saveOutputImage(inputFile);
    }

    @FXML
    private void onResetAction(ActionEvent event) {
        hiderModel.reset();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (InputStream is = getClass().getResourceAsStream("/images/placeholder.png")) {
            hiderModel = new HiderModel(new Image(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

        maxFileSizeLabel.visibleProperty().bind(hiderModel.inputImageProperty().isNotEqualTo(hiderModel.getPlaceholderImage()));
        maxFileSizeLabel.textProperty().bindBidirectional(hiderModel.inputImageProperty(), new StringImageMaxInputFileSize());

        inputFilePathLabel.visibleProperty().bind(inputFilePathLabel.textProperty().isNotEmpty());

        inputFilePathLabel.textProperty().bind(hiderModel.inputFileProperty().asString());
        inputFilePathLabel.visibleProperty().bind(hiderModel.inputFileProperty().isNotNull());

        encryptButton.disableProperty().bind(
                hiderModel.inputFileProperty().isNull()
                        .or(hiderModel.passwordProperty().isEmpty())
                        .or(hiderModel.inputImageProperty().isNull())
                        .or(hiderModel.inputImageProperty().isEqualTo(hiderModel.getPlaceholderImage()))
        );

        saveResultImageButton.disableProperty().bind(
                hiderModel.outputImageProperty().isEqualTo(hiderModel.getPlaceholderImage())
        );

        hiderModel.inputImageProperty().bindBidirectional(sourceImageView.imageProperty());
        hiderModel.passwordProperty().bindBidirectional(passwordField.textProperty());
        hiderModel.outputImageProperty().bindBidirectional(resultImageView.imageProperty());

        hiderModel.reset();
    }
}
