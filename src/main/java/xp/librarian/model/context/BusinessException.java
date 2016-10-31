package xp.librarian.model.context;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * @author xp
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 7872506909592221398L;

    @Getter
    private ErrorCode key;

    public BusinessException(ErrorCode key) {
        this(key, null);
    }

    public BusinessException(ErrorCode key, Throwable cause) {
        super(Optional.ofNullable(key).map(ErrorCode::getKey).orElse(StringUtils.EMPTY), cause);
        this.key = key;
    }

    @Override
    public String getLocalizedMessage() {
        //TODO I18N
        return super.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
