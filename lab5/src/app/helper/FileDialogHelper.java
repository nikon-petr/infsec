package app.helper;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileDialogHelper {

    public enum ExtensionFilters {
        XML, PNG, TXT, PDF, WORD, ANY
    }

    private static final Map<ExtensionFilters, FileChooser.ExtensionFilter> extensionFilters;

    static {
        extensionFilters = new HashMap<>();
        extensionFilters.put(ExtensionFilters.XML, new FileChooser.ExtensionFilter("Xml Files (*.xml)", "*.xml"));
        extensionFilters.put(ExtensionFilters.PNG, new FileChooser.ExtensionFilter("Png Image Files (*.png)", "*.png"));
        extensionFilters.put(ExtensionFilters.TXT, new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"));
        extensionFilters.put(ExtensionFilters.PDF, new FileChooser.ExtensionFilter("Print Document Files (*.pdf)", "*.pdf"));
        extensionFilters.put(ExtensionFilters.WORD, new FileChooser.ExtensionFilter("Word Files (*.doc, *.docx)", "*.doc", "*.docx"));
        extensionFilters.put(ExtensionFilters.ANY, new FileChooser.ExtensionFilter("All Files (*)", "*", "*.*"));
    }

    public static File chooseFile(Window ownerWindow, String dialogTitle, File initialDirectory, ExtensionFilters... extensionFilters) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(dialogTitle);
        fileChooser.setInitialDirectory(initialDirectory);

        for (ExtensionFilters e : extensionFilters) {
            if (FileDialogHelper.extensionFilters.get(e) != null) {
                fileChooser.getExtensionFilters().add(FileDialogHelper.extensionFilters.get(e));
            }
        }

        return fileChooser.showOpenDialog(ownerWindow);
    }

    public static File chooseFile(Window ownerWindow, String dialogTitle, ExtensionFilters... extensionFilters) {
        File homeDirectory = new File(System.getProperty("user.home"));
        return chooseFile(ownerWindow, dialogTitle, homeDirectory, extensionFilters);
    }

    public static File chooseFile(String dialogTitle, ExtensionFilters... extensionFilter) {
        File homeDirectory = new File(System.getProperty("user.home"));
        return chooseFile(null, dialogTitle, homeDirectory, extensionFilter);
    }

    public static File chooseDirectory(Window ownerWindow, String dialogTitle, File initialDirectory) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setTitle(dialogTitle);
        directoryChooser.setInitialDirectory(initialDirectory);

        return directoryChooser.showDialog(ownerWindow);
    }

    public static File chooseDirectory(Window ownerWindow, String dialogTitle) {
        File userDirectory = new File(System.getProperty("user.home"));
        return chooseDirectory(ownerWindow, dialogTitle, userDirectory);
    }

    public static File chooseDirectory(String dialogTitle) {
        File userDirectory = new File(System.getProperty("user.home"));
        return chooseDirectory(null, dialogTitle, userDirectory);
    }

    public static File saveFile(Window ownerWindow, String dialogTitle, String defaultFileName, ExtensionFilters... extensionFilters) {
        FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setInitialFileName(defaultFileName);
        fileChooser.setTitle(dialogTitle);

        for (ExtensionFilters e : extensionFilters) {
            if (FileDialogHelper.extensionFilters.get(e) != null) {
                fileChooser.getExtensionFilters().add(FileDialogHelper.extensionFilters.get(e));
            }
        }

        return fileChooser.showSaveDialog(ownerWindow);
    }

    public static File saveFile(String dialogTitle, String defaultFileName, ExtensionFilters... extensionFilters) {
        return saveFile(null, dialogTitle, defaultFileName, extensionFilters);
    }
}
