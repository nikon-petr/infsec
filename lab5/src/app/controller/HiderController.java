package app.controller;

import app.helper.AlertHelper;
import app.helper.FileDialogHelper;
import app.helper.StringImageMaxInputFileSize;
import app.viewmodel.FileTooLongException;
import app.viewmodel.HiderViewModel;
import app.viewmodel.ImageTooSmallException;
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

    private HiderViewModel hiderViewModel;

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
        File inputImageFile = FileDialogHelper.chooseFile("Open Input Image", FileDialogHelper.ExtensionFilter.PNG);
        if (inputImageFile != null) {
            try (InputStream inputFileStream = new FileInputStream(inputImageFile)) {
                hiderViewModel.setInputImage(new Image(inputFileStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onOpenInputFileAction(ActionEvent event) {
        File inputFile = FileDialogHelper.chooseFile("Open Input File", FileDialogHelper.ExtensionFilter.ANY);
        hiderViewModel.setInputFile(inputFile);
    }

    @FXML
    private void onEncryptAction(ActionEvent event) {
        try {
            hiderViewModel.hideData();
        } catch (FileTooLongException | ImageTooSmallException e) {
            AlertHelper.alert(Alert.AlertType.WARNING, "Warning", null, e.getMessage());
        } catch (FileNotFoundException e) {
            AlertHelper.alert(Alert.AlertType.ERROR, "Error", null, "File not found");
        }
    }

    @FXML
    private void onSaveAsAction(ActionEvent event) {
        File inputFile = FileDialogHelper.saveFile("Save result image", "result.png", FileDialogHelper.ExtensionFilter.PNG);
        hiderViewModel.saveOutputImage(inputFile);
    }

    @FXML
    private void onResetAction(ActionEvent event) {
        hiderViewModel.reset();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (InputStream is = getClass().getResourceAsStream("/images/placeholder.png")) {
            hiderViewModel = new HiderViewModel(new Image(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

        maxFileSizeLabel.visibleProperty().bind(hiderViewModel.inputImageProperty().isNotEqualTo(hiderViewModel.getPlaceholderImage()));
        maxFileSizeLabel.textProperty().bindBidirectional(hiderViewModel.inputImageProperty(), new StringImageMaxInputFileSize());

        inputFilePathLabel.visibleProperty().bind(inputFilePathLabel.textProperty().isNotEmpty());

        inputFilePathLabel.textProperty().bind(hiderViewModel.inputFileProperty().asString());
        inputFilePathLabel.visibleProperty().bind(hiderViewModel.inputFileProperty().isNotNull());

        encryptButton.disableProperty().bind(
                hiderViewModel.inputFileProperty().isNull()
                        .or(hiderViewModel.passwordProperty().isEmpty())
                        .or(hiderViewModel.inputImageProperty().isNull())
                        .or(hiderViewModel.inputImageProperty().isEqualTo(hiderViewModel.getPlaceholderImage()))
        );

        saveResultImageButton.disableProperty().bind(
                hiderViewModel.outputImageProperty().isEqualTo(hiderViewModel.getPlaceholderImage())
        );

        hiderViewModel.inputImageProperty().bindBidirectional(sourceImageView.imageProperty());
        hiderViewModel.passwordProperty().bindBidirectional(passwordField.textProperty());
        hiderViewModel.outputImageProperty().bindBidirectional(resultImageView.imageProperty());

        hiderViewModel.reset();
    }
}
