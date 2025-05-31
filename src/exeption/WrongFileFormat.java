package exeption;

public class WrongFileFormat extends Exception {

    public WrongFileFormat(String message) {
        super(message);
    }

    public WrongFileFormat(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongFileFormat(Throwable cause) {
        super(cause);
    }
}
