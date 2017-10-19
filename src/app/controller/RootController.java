package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private Label fileNameLabel;

    @FXML
    private BarChart encodingChart;

    @FXML
    private BarChart decodingChart;

    @FXML
    private void handleFileButtonAction(ActionEvent e){

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        encodingChart.getYAxis().setLabel("Time");
        encodingChart.getXAxis().setLabel("Method");

        decodingChart.getXAxis().setLabel("Time");
        decodingChart.getYAxis().setLabel("Method");
    }
}
