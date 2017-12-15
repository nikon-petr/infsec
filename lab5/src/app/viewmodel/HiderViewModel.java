package app.viewmodel;

import app.data.Crypter;
import app.data.HashCalculator;
import app.data.Hider;
import app.helper.SaveImageHelper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;

import java.io.*;

public class HiderViewModel {

    private Image placeholderImage;

    private ObjectProperty<Image> inputImageProperty;

    private ObjectProperty<File> inputFileProperty;

    private SimpleStringProperty passwordProperty;

    private ObjectProperty<Image> outputImageProperty;

    public HiderViewModel(Image placeholderImage) {
        this.placeholderImage = placeholderImage;
        inputImageProperty = new SimpleObjectProperty<>(placeholderImage);
        inputFileProperty = new SimpleObjectProperty<>();
        passwordProperty = new SimpleStringProperty();
        outputImageProperty = new SimpleObjectProperty<>(placeholderImage);
    }

    public void hideData() throws FileTooLongException, ImageTooSmallException, FileNotFoundException {
        int maxDataSize = (int) (getInputImage().getWidth() * getInputImage().getHeight());
        long dataSize = getInputFile().length();

        if (dataSize > 100 * 1024 * 1024) {
            throw new FileTooLongException("The file is too long");
        }

        if (maxDataSize < dataSize) {
            throw new ImageTooSmallException("The image is too small for hide the data");
        }

        try (InputStream inputStream = new FileInputStream(getInputFile())) {

            byte[] passwordHash = HashCalculator.calculateHash(getPassword());
            InputStream encodedStream = Crypter.encrypt(inputStream, passwordHash);
            Image outputImage = Hider.hideData(getInputImage(), encodedStream);

            setOutputImage(outputImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveOutputImage(File outputFile) {
        PixelFormat.Type type = getInputImage().getPixelReader().getPixelFormat().getType();
        boolean isRgb = type == PixelFormat.Type.BYTE_RGB;
        SaveImageHelper.saveImageToPngFile(getOutputImage(), outputFile, isRgb);
    }

    public void reset() {
        inputImageProperty.set(placeholderImage);
        inputFileProperty.set(null);
        passwordProperty.set(null);
        outputImageProperty.set(placeholderImage);
    }

    public Image getPlaceholderImage() {
        return placeholderImage;
    }

    public void setPlaceholderImage(Image placeholderImage) {
        this.placeholderImage = placeholderImage;
    }

    public void setInputImage(Image inputImage) {
        inputImageProperty.set(inputImage);
    }

    public Image getInputImage() {
        return inputImageProperty.get();
    }

    public void setInputFile(File inputFile) {
        inputFileProperty.set(inputFile);
    }

    public File getInputFile() {
        return inputFileProperty.get();
    }

    public void setPassword(String password) {
        passwordProperty.set(password);
    }

    public String getPassword() {
        return passwordProperty.get();
    }

    public void setOutputImage(Image outputImage) {
        outputImageProperty.set(outputImage);
    }

    public Image getOutputImage() {
        return outputImageProperty.get();
    }

    public ObjectProperty<Image> inputImageProperty() {
        return inputImageProperty;
    }

    public ObjectProperty<File> inputFileProperty() {
        return inputFileProperty;
    }

    public SimpleStringProperty passwordProperty() {
        return passwordProperty;
    }

    public ObjectProperty<Image> outputImageProperty() {
        return outputImageProperty;
    }
}
