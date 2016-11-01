package xp.librarian.model.context;

/**
 * @author xp
 */
public class InternalServerException extends RuntimeException {

    private static final long serialVersionUID = -7156163369713202580L;

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
