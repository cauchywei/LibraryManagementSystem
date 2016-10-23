package xp.librarian.model.context;

/**
 * @author xp
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 7872506909592221398L;

    private ErrorCode key;

    public BusinessException(ErrorCode key) {
        this(key, null);
    }

    public BusinessException(ErrorCode key, Throwable cause) {
        super(cause);
        this.key = key;
    }

    @Override
    public String getMessage() {
        //TODO I18N
        return key.getKey();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
