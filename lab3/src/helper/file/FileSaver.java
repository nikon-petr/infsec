package helper.file;

import java.io.File;

public class FileSaver {
    public static javafx.stage.FileChooser get(String title){
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle(title);
        return fileChooser;
    }
}
