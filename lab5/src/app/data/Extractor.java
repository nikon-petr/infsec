package app.data;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;

import java.io.IOException;
import java.io.OutputStream;

public class Extractor {
    public static void extract(Image inputImage, OutputStream dataStream) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();

        PixelReader pixelReader = inputImage.getPixelReader();

        int[] pixels = new int[width * height];
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);

        SteganographyInputStream steganographyInputStream = new SteganographyInputStream(pixels);

        try {
            int data = steganographyInputStream.read();
            while (data != -1) {
                dataStream.write(data);
                data = steganographyInputStream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
