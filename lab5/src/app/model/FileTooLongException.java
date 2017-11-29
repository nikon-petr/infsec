package app.model;

public class FileTooLongException extends Exception {

    public FileTooLongException() {
    }

    public FileTooLongException(String message) {
        super(message);
    }
}
