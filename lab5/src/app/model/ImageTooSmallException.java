package app.model;

public class ImageTooSmallException extends Exception {
    public ImageTooSmallException() {}

    public ImageTooSmallException(String message){
        super(message);
    }
}
