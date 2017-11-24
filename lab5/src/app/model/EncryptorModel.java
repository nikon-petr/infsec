package app.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.File;

public class EncryptorModel {

    private ObjectProperty<Image> inputImageProperty;

    private ObjectProperty<File> inputFileProperty;

    private SimpleBooleanProperty isUsingPasswordProperty;

    private SimpleStringProperty passwordProperty;

    private ObjectProperty<Image> outputImageProperty;

    private ObjectProperty<File> outputFileProperty;

    public EncryptorModel() {
        inputImageProperty = new SimpleObjectProperty<>();
        inputFileProperty = new SimpleObjectProperty<>();
        isUsingPasswordProperty = new SimpleBooleanProperty();
        passwordProperty = new SimpleStringProperty();
        outputImageProperty = new SimpleObjectProperty<>();
        outputFileProperty = new SimpleObjectProperty<>();
    }

    public void setInputImage(Image inputImage){
        inputImageProperty.set(inputImage);
    }

    public Image getInputImage(){
        return inputImageProperty.get();
    }

    public void setInputFile(File inputFile){
        inputFileProperty.set(inputFile);
    }

    public File getInputFile(){
        return inputFileProperty.get();
    }

    public void setIsUsingPassword(boolean isUsingPassword){
        isUsingPasswordProperty.set(isUsingPassword);
    }

    public boolean getIsUsingPassword(){
        return isUsingPasswordProperty.get();
    }

    public void setPassword(String password){
        passwordProperty.set(password);
    }

    public String getPassword(){
        return passwordProperty.get();
    }

    public void setOutputImage(Image outputImage){
        outputImageProperty.set(outputImage);
    }

    public Image getOutputImage(){
        return outputImageProperty.get();
    }

    public void setOutputFile(File outputFile){
        outputFileProperty.set(outputFile);
    }

    public File getOutputFile(){
        return outputFileProperty.get();
    }

    public ObjectProperty<Image> inputImageProperty() {
        return inputImageProperty;
    }

    public ObjectProperty<File> inputFileProperty() {
        return inputFileProperty;
    }

    public SimpleBooleanProperty isUsingPasswordProperty() {
        return isUsingPasswordProperty;
    }

    public SimpleStringProperty passwordProperty() {
        return passwordProperty;
    }

    public ObjectProperty<Image> outputImageProperty() {
        return outputImageProperty;
    }

    public ObjectProperty<File> outputFileProperty() {
        return outputFileProperty;
    }
}
