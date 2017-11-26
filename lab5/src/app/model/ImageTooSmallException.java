package app.model;

public class ImageTooSmall extends Exception {
    public ImageTooSmall() {}

    public ImageTooSmall(String message){
        super(message);
    }
}
