package xp.librarian.handler;

import java.time.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xp
 */
@RestControllerAdvice
public class RequestDataBinder {

    public static class CustomInstantEditor extends PropertiesEditor {

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (StringUtils.isEmpty(text)) {
                setValue(null);
            } else {
                setValue(Instant.ofEpochMilli(Long.parseLong(text)));
            }
        }

        @Override
        public String getAsText() {
            return Optional.ofNullable((Instant) getValue())
                    .map(e -> String.valueOf(e.toEpochMilli()))
                    .orElse(StringUtils.EMPTY);
        }
    }

    @InitBinder
    private void registerBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Instant.class, new CustomInstantEditor());
    }

}
