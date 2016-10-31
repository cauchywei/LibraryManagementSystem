package xp.librarian.model.context;

/**
 * @author xp
 */
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -7424380455806394517L;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
