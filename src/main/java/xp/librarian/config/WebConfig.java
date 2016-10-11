package xp.librarian.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author xp
 */
@Configuration
@Import({ServiceConfig.class})
@ComponentScan(
        basePackages = {
                "xp.librarian.controller",
                "xp.librarian.handler"
        },
        useDefaultFilters = false,
        includeFilters = {
            @ComponentScan.Filter(Controller.class),
            @ComponentScan.Filter(ControllerAdvice.class),
        }
)
public class WebConfig {

}
