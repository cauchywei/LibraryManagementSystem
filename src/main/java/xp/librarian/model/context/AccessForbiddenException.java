package xp.librarian.model.context;

/**
 * @author xp
 */
public class AccessForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 916775889525216995L;

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
