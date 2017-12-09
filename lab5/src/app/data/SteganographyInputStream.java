package app.data;

import java.io.IOException;
import java.io.InputStream;

public class SteganographyInputStream extends InputStream {

    private int[] pixels;
    private int pointer = 1;

    private SteganographyInputStream() {}

    public SteganographyInputStream(int[] pixels) {
        this.pixels = pixels;
    }

    @Override
    public synchronized int read() throws IOException {
        if (pointer == ~pixels[0]){
            System.out.println(~pixels[0]);
            return -1;
        }

        int result = (pixels[pointer] & 0x00070000) >>> 11;
        result += (pixels[pointer] & 0x00000700) >>> 6;
        result += pixels[pointer] & 0x00000003;
        pointer++;

        return result;
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
