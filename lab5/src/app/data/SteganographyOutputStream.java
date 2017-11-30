package app.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SteganographyOutputStream extends OutputStream {

    private InputStream inputStream;

    private SteganographyOutputStream() {}

    public SteganographyOutputStream(InputStream inputStream) {
        super();
        this.inputStream = inputStream;
    }

    @Override
    public synchronized void write(byte[] bytes, int offset, int length) throws IOException {

    }

    @Override
    public synchronized void write(byte[] bytes) throws IOException {

    }

    @Override
    public synchronized void write(int bytes) throws IOException {

    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
