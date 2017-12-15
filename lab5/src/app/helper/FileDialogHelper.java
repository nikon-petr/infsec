package app.helper;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileDialogHelper {

    public enum ExtensionFilter {
        ANY, TXT, XML, PNG, PDF, WORD
    }

    private static final Map<ExtensionFilter, FileChooser.ExtensionFilter> extensionFilters;

    static {
        Map<ExtensionFilter, FileChooser.ExtensionFilter> ef = new HashMap<>();
        ef.put(ExtensionFilter.XML, new FileChooser.ExtensionFilter("Xml Files (*.xml)", "*.xml"));
        ef.put(ExtensionFilter.PNG, new FileChooser.ExtensionFilter("Png Image Files (*.png)", "*.png"));
        ef.put(ExtensionFilter.TXT, new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"));
        ef.put(ExtensionFilter.PDF, new FileChooser.ExtensionFilter("Print Document Files (*.pdf)", "*.pdf"));
        ef.put(ExtensionFilter.WORD, new FileChooser.ExtensionFilter("Word Files (*.doc, *.docx)", "*.doc", "*.docx"));
        ef.put(ExtensionFilter.ANY, new FileChooser.ExtensionFilter("All Files (*)", "*", "*.*"));

        extensionFilters = Collections.unmodifiableMap(ef);
    }

    public static File chooseFile(Window ownerWindow, String dialogTitle, File initialDirectory, ExtensionFilter... extensionFilters) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(dialogTitle);
        fileChooser.setInitialDirectory(initialDirectory);

        for (ExtensionFilter extensionFilter : extensionFilters) {
            if (FileDialogHelper.extensionFilters.get(extensionFilter) != null) {
                fileChooser.getExtensionFilters().add(FileDialogHelper.extensionFilters.get(extensionFilter));
            }
        }

        return fileChooser.showOpenDialog(ownerWindow);
    }

    public static File chooseFile(Window ownerWindow, String dialogTitle, ExtensionFilter... extensionFilters) {
        File homeDirectory = new File(System.getProperty("user.home"));
        return chooseFile(ownerWindow, dialogTitle, homeDirectory, extensionFilters);
    }

    public static File chooseFile(String dialogTitle, ExtensionFilter... extensionFilter) {
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

    public static File saveFile(Window ownerWindow, String dialogTitle, String defaultFileName, ExtensionFilter... extensionFilters) {
        FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setInitialFileName(defaultFileName);
        fileChooser.setTitle(dialogTitle);

        for (ExtensionFilter extensionFilter : extensionFilters) {
            if (FileDialogHelper.extensionFilters.get(extensionFilter) != null) {
                fileChooser.getExtensionFilters().add(FileDialogHelper.extensionFilters.get(extensionFilter));
            }
        }

        return fileChooser.showSaveDialog(ownerWindow);
    }

    public static File saveFile(String dialogTitle, String defaultFileName, ExtensionFilter... extensionFilters) {
        return saveFile(null, dialogTitle, defaultFileName, extensionFilters);
    }
}
