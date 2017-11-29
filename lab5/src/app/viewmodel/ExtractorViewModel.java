package app.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class ExtractorViewModel {

    private Image placeholderImage;

    private ObjectProperty<Image> inputImageProperty;

    private SimpleStringProperty passwordProperty;

    public ExtractorViewModel(Image placeholderImage) {
        this.placeholderImage = placeholderImage;
        inputImageProperty = new SimpleObjectProperty<>();
        passwordProperty = new SimpleStringProperty();
    }

    public void reset() {
        inputImageProperty.set(placeholderImage);
        passwordProperty.set(null);
    }

    public Image getPlaceholderImage() {
        return placeholderImage;
    }

    public void setPlaceholderImage(Image placeholderImage) {
        this.placeholderImage = placeholderImage;
    }

    public Image getInputImage() {
        return inputImageProperty.get();
    }

    public void setInputImage(Image inputImageProperty) {
        this.inputImageProperty.set(inputImageProperty);
    }

    public String getPassword() {
        return passwordProperty.get();
    }

    public void setPassword(String passwordProperty) {
        this.passwordProperty.set(passwordProperty);
    }

    public SimpleStringProperty passwordProperty() {
        return passwordProperty;
    }

    public ObjectProperty<Image> inputImageProperty() {
        return inputImageProperty;
    }
}
