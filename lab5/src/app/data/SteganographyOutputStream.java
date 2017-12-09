package app.data;

import java.io.IOException;
import java.io.OutputStream;

public class SteganographyOutputStream extends OutputStream {

    private int[] pixels;
    private int pointer = 1;

    private SteganographyOutputStream() {}

    public SteganographyOutputStream(int[] pixels) {
        this.pixels = new int[pixels.length];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
    }

    @Override
    public synchronized void write(int b) throws IOException {
        pixels[pointer] = (pixels[pointer] & 0xFFF8FFFF) + ((b & 0x000000E0) << 11);
        pixels[pointer] = (pixels[pointer] & 0xFFFFF8FF) + ((b & 0x0000001C) << 6);
        pixels[pointer] = (pixels[pointer] & 0xFFFFFFFC) + (b & 0x00000003);
        pointer++;
    }

    public int[] toIntArray(){
        pixels[0] = ~pointer;
        return pixels;
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
