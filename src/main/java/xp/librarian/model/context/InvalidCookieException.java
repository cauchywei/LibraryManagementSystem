package xp.librarian.model.context;

/**
 * @author xp
 */
public class InvalidCookieException extends RuntimeException {

    private static final long serialVersionUID = -6570034095418544213L;

    public InvalidCookieException(String message) {
        super(message);
    }

    public InvalidCookieException(Throwable cause) {
        super(cause);
    }

    public InvalidCookieException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
