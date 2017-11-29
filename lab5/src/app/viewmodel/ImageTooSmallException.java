package app.viewmodel;

public class ImageTooSmallException extends Exception {

    public ImageTooSmallException() {
    }

    public ImageTooSmallException(String message) {
        super(message);
    }
}
