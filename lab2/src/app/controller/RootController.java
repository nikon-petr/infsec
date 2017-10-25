package app.controller;

import app.model.CryptoMethodTester;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private Label fileNameLabel;

    @FXML
    private Button testButton;

    @FXML
    private BarChart encodingChart;

    @FXML
    private BarChart decodingChart;

    @FXML
    private void handleFileButtonAction(ActionEvent e) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text files (*txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File userDirectory = new File(System.getProperty("user.home"));

        fileChooser.setTitle("Text file");
        fileChooser.setInitialDirectory(userDirectory);
        File selectedDirectory = fileChooser.showOpenDialog(null);

        if (selectedDirectory != null) {
            fileNameLabel.setText(selectedDirectory.toString());
        }
    }

    @FXML
    private void handleTestButtonAction(ActionEvent e) {
        File inputFile = new File(fileNameLabel.getText());
        if (!inputFile.exists()) {
            return;
        }
        CryptoMethodTester tester = new CryptoMethodTester();

        testButton.setDisable(true);

        Task<Void> codeTask = new Task<Void>() {
            @Override
            public Void call(){

                tester.test(inputFile);
                return null;
            }
        };
        codeTask.setOnSucceeded(event -> {
            encodingChart.getData().addAll(tester.getEserieses());
            decodingChart.getData().addAll(tester.getDserieses());

            testButton.setDisable(false);
        });
        new Thread(codeTask).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        encodingChart.getYAxis().setLabel("Time, ms");
        encodingChart.getXAxis().setLabel("Method");

        encodingChart.setAnimated(false);

        decodingChart.getYAxis().setLabel("Time, ms");
        decodingChart.getXAxis().setLabel("Method");

        decodingChart.setAnimated(false);
    }
}
