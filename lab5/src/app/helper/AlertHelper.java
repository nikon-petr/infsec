package app.helper;

import javafx.scene.control.Alert;

public final class AlertHelper {

    private AlertHelper() {}

    public static void alert(Alert.AlertType alertType, String alertTitle, String alertHeader, String alertContent) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);

        alert.showAndWait();
    }
}
