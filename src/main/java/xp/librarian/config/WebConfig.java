package xp.librarian.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import xp.librarian.interceptor.SampleInterceptor;

/**
 * @author xp
 */
@Configuration
@Import({
        ServiceConfig.class,
        SwaggerConfig.class
})
@EnableWebMvc
@ComponentScan(
        basePackages = {
                "xp.librarian.filter",
                "xp.librarian.interceptor",
                "xp.librarian.handler",
                "xp.librarian.controller",
        }
)
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SampleInterceptor());
    }
}
