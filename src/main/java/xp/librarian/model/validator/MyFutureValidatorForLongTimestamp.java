package xp.librarian.model.validator;

import java.time.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author xp
 */
public class MyFutureValidatorForLongTimestamp implements ConstraintValidator<MyFuture, Long> {

    @Override
    public void initialize(MyFuture future) {
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return aLong == null || aLong > Instant.now().toEpochMilli();
    }
}
