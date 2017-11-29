package app.model;

import javafx.scene.image.Image;
import javafx.util.StringConverter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class StringImageMaxInputFileSize extends StringConverter<Image> {

    @Override
    public String toString(Image image) {
        if (image != null) {
            return "Max file size: " + String.format("%.1f", image.getWidth() * image.getHeight() / (4 * 1024)) + " KB";
        } else {
            return null;
        }
    }

    @Override
    public Image fromString(String s) {
        throw new NotImplementedException();
    }
}
