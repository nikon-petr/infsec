package app.model;

import app.data.Encryptor;
import app.data.HashCalculator;
import app.data.Hider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidKeyException;

public class HiderModel {

    private Image placeholderImage;

    private ObjectProperty<Image> inputImageProperty;

    private ObjectProperty<File> inputFileProperty;

    private SimpleStringProperty passwordProperty;

    private ObjectProperty<Image> outputImageProperty;

    public HiderModel(Image placeholderImage) {
        this.placeholderImage = placeholderImage;
        inputImageProperty = new SimpleObjectProperty<>(placeholderImage);
        inputFileProperty = new SimpleObjectProperty<>();
        passwordProperty = new SimpleStringProperty();
        outputImageProperty = new SimpleObjectProperty<>(placeholderImage);
    }

    public void hideData() throws FileTooLongException, ImageTooSmall, FileNotFoundException {
        int maxDataSize = (int) (getInputImage().getWidth() * getInputImage().getHeight()) / 4;
        long dataSize = getInputFile().length();

        if (dataSize > 100 * 1024 * 1024) {
            throw new FileTooLongException("The file is too long");
        }


        if (maxDataSize < dataSize) {
            throw new ImageTooSmall("The image is too small for hide the data");
        }

        try (InputStream inputStream = new FileInputStream(getInputFile())) {

            byte[] passwordHash = HashCalculator.calculateHash(getPassword());
            InputStream encodedStream = Encryptor.encrypt(inputStream, passwordHash);
            Image outputImage = Hider.hideData(getInputImage(), encodedStream);

            setOutputImage(outputImage);

        } catch (IOException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void saveOutputImage(File outputFile) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(getOutputImage(), null);
        try {
            ImageIO.write(bufferedImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset() {
        inputImageProperty.set(placeholderImage);
        inputFileProperty.set(null);
        passwordProperty.set(null);
        outputImageProperty.set(placeholderImage);
    }

    public int getMaxFileSize() {
        return (int) (getInputImage().getWidth() * getInputImage().getHeight()) / 4;
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
