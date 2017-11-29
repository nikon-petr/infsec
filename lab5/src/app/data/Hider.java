package app.data;

import javafx.scene.image.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Hider {

    public static Image hideData(Image inputImage, InputStream inputStream) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();

        WritableImage result = new WritableImage(width, height);


        PixelReader pixelReader = inputImage.getPixelReader();
        PixelWriter pixelWriter = result.getPixelWriter();

        System.out.println(pixelReader.getPixelFormat().getType());

        byte[] pixels = new byte[width * height * 4];
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), pixels, 0, width * 4);

        try {
            int offset = 4;
            byte[] buffer = new byte[2048];

            while (inputStream.read(buffer) != -1) {
                for (int i = 0; i < 2048; i++, offset += 4) {
                    pixels[offset] = (byte) ((pixels[offset] & 0b11111000) + ((buffer[i] & 0b11100000) >>> 5));
                    pixels[offset + 1] = (byte) ((pixels[offset + 1] & 0b11111000) + ((buffer[i] & 0b00011100) >>> 2));
                    pixels[offset + 2] = (byte) ((pixels[offset + 2] & 0b11111100) + ((buffer[i] & 0b00000011)));
                }
            }

            byte[] length = ByteBuffer.allocate(4).putInt(offset).array();
            System.arraycopy(length, 0, pixels, 0, 4);

        } catch (IOException e) {
            e.printStackTrace();
        }

        pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), pixels, 0, width * 4);

        return result;
    }
}
