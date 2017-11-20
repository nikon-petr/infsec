package helper.file;

public class FileSaver {
    public static javafx.stage.FileChooser get(String title){
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle(title);
        return fileChooser;
    }

    public static javafx.stage.FileChooser get(String title, String defaultFileName){
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setInitialFileName(defaultFileName);
        fileChooser.setTitle(title);
        return fileChooser;
    }
}
