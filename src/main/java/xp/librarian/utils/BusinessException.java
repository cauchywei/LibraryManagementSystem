package xp.librarian.utils;

/**
 * @author xp
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 7872506909592221398L;

    private String key;

    public BusinessException(String key) {
        this(key, null);
    }

    public BusinessException(String key, Throwable cause) {
        super(cause);
        this.key = key;
    }

    @Override
    public String getMessage() {
        return key;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
