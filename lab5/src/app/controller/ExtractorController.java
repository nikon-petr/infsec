package app.controller;

import app.helper.FileDialogHelper;
import app.viewmodel.ExtractorViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ExtractorController implements Initializable {

    private ExtractorViewModel extractorViewModel;

    @FXML
    private ImageView inputImageView;

    @FXML
    private Button openImageButton, extractButton, resetButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onOpenImageAction(ActionEvent event) {
        File inputImageFile = FileDialogHelper.chooseFile(
                "Open Input Image",
                FileDialogHelper.ExtensionFilter.PNG,
                FileDialogHelper.ExtensionFilter.BMP
        );
        if (inputImageFile != null) {
            try (InputStream inputFileStream = new FileInputStream(inputImageFile)) {
                extractorViewModel.setInputImage(new Image(inputFileStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onExtractAsAction(ActionEvent event) {
        File outputFile = FileDialogHelper.saveFile(
                "Extract as",
                "extracted",
                FileDialogHelper.ExtensionFilter.TXT,
                FileDialogHelper.ExtensionFilter.PDF,
                FileDialogHelper.ExtensionFilter.WORD,
                FileDialogHelper.ExtensionFilter.ANY
        );

        if (outputFile != null) {
            try (OutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                extractorViewModel.extractFile(fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onResetAction(ActionEvent event) {
        extractorViewModel.reset();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (InputStream is = getClass().getResourceAsStream("/images/placeholder.png")) {
            extractorViewModel = new ExtractorViewModel(new Image(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

        extractButton.disableProperty().bind(
                extractorViewModel.passwordProperty().isEmpty()
                        .or(extractorViewModel.inputImageProperty().isNull())
                        .or(extractorViewModel.inputImageProperty().isEqualTo(extractorViewModel.getPlaceholderImage()))
        );

        extractorViewModel.inputImageProperty().bindBidirectional(inputImageView.imageProperty());
        extractorViewModel.passwordProperty().bindBidirectional(passwordField.textProperty());

        extractorViewModel.reset();
    }
}
