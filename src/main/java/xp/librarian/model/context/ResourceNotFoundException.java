package xp.librarian.model.context;

/**
 * @author xp
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8996109177639717683L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
