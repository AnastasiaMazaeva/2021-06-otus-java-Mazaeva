package exceptions;

public class AtmException extends RuntimeException {

    public AtmException() {
        super("Невозможно выдать требуемую сумму");
    }
}
