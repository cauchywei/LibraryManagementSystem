package xp.librarian.handler;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xp
 */
@RestControllerAdvice
public class ValidatorBinder {

    @Validated
    public static class InstantValidator implements Validator {

        @Override
        public boolean supports(Class<?> aClass) {
            return false;
        }

        @Override
        public void validate(Object o, Errors errors) {

        }
    }

}
