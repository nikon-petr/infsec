package helper.file;

import java.io.File;

public class FileChooser {
    public static javafx.stage.FileChooser get(String title, String extTitle, String... extPatterns){
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();

        File userDirectory = new File(System.getProperty("user.home"));

        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(userDirectory);

        javafx.stage.FileChooser.ExtensionFilter extensionFilter = new javafx.stage.FileChooser.ExtensionFilter(extTitle, extPatterns);
        fileChooser.getExtensionFilters().add(extensionFilter);

        return fileChooser;
    }
}
