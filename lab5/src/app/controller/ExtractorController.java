package app.controller;

import app.helper.FileDialogHelper;
import app.model.ExtractorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ExtractorController implements Initializable {

    private ExtractorModel extractorModel;

    @FXML
    private ImageView inputImageView;

    @FXML
    private Button openImageButton, extractButton, resetButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onOpenImageAction(ActionEvent event) {
        File inputImageFile = FileDialogHelper.chooseFile("Open Input Image", FileDialogHelper.ExtensionFilters.PNG);
        if (inputImageFile != null) {
            try (InputStream inputFileStream = new FileInputStream(inputImageFile)) {
                extractorModel.setInputImage(new Image(inputFileStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onExtractAsAction(ActionEvent event) {

    }

    @FXML
    private void onResetAction(ActionEvent event) {
        extractorModel.reset();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (InputStream is = getClass().getResourceAsStream("/images/placeholder.png")) {
            extractorModel = new ExtractorModel(new Image(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

        extractButton.disableProperty().bind(
                extractorModel.passwordProperty().isEmpty()
                        .or(extractorModel.inputImageProperty().isNull())
                        .or(extractorModel.inputImageProperty().isEqualTo(extractorModel.getPlaceholderImage()))
        );

        extractorModel.inputImageProperty().bindBidirectional(inputImageView.imageProperty());
        extractorModel.passwordProperty().bindBidirectional(passwordField.textProperty());

        extractorModel.reset();
    }
}
