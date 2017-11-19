package helper.file;

import java.io.File;

public class DirectoryChooser {

    public static javafx.stage.DirectoryChooser get(String title) {

        javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();

        File userDirectory = new File(System.getProperty("user.home"));

        directoryChooser.setTitle(title);
        directoryChooser.setInitialDirectory(userDirectory);

        return directoryChooser;
    }
}
