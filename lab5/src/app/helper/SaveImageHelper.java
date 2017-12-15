package app.helper;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class SaveImageHelper {

    private SaveImageHelper() {}

    public static void saveImageToPngFile(Image image, File file, Boolean isRgb) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            if (isRgb) {
                bufferedImage = new BufferedImage(
                        ((int) image.getWidth()),
                        ((int) image.getHeight()),
                        BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g2 = bufferedImage.createGraphics();
                g2.drawImage(SwingFXUtils.fromFXImage(image, null), 0, 0, null);
                g2.dispose();
            }

            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
