package app.data;

import javafx.scene.image.*;

import java.io.IOException;
import java.io.InputStream;

public final class Hider {

    private Hider() {}

    public static Image hideData(Image inputImage, InputStream inputStream) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();

        WritableImage result = new WritableImage(width, height);

        PixelReader pixelReader = inputImage.getPixelReader();
        PixelWriter pixelWriter = result.getPixelWriter();

        int[] pixels = new int[width * height];
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);

        try (SteganographyOutputStream steganographyOutputStream = new SteganographyOutputStream(pixels)) {

            int data = inputStream.read();
            while (data != -1) {
                steganographyOutputStream.write(data);
                data = inputStream.read();
            }

            int[] mpixels = steganographyOutputStream.toIntArray();
            pixelWriter.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), mpixels, 0, width);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
