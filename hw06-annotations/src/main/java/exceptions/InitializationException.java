package exceptions;

public class InitializationException extends RuntimeException {
    public InitializationException(String message, Exception e) {
        super(message, e);
    }
}
