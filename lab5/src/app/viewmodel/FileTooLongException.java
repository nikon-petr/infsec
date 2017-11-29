package app.viewmodel;

public class FileTooLongException extends Exception {

    public FileTooLongException() {
    }

    public FileTooLongException(String message) {
        super(message);
    }
}
