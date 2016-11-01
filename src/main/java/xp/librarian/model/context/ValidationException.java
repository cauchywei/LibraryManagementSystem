package xp.librarian.model.context;

import java.util.*;

import javax.validation.ConstraintViolation;

import lombok.Getter;

/**
 * @author xp
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 3910454411201282029L;

    @Getter
    private Set<String> messages;

    public <T> ValidationException(Set<ConstraintViolation<T>> violations) {
        messages = new HashSet<>(violations.size());
        violations.forEach(e -> {
            messages.add(e.getMessage());
        });
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
