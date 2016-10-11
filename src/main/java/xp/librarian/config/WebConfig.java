package xp.librarian.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import xp.librarian.interceptor.SimpleCorsInterceptor;

/**
 * @author xp
 */
@Configuration
@Import({ServiceConfig.class})
@ComponentScan(
        basePackages = {
                "xp.librarian.controller",
                "xp.librarian.handler",
                "xp.librarian.interceptor"
        }
)
public class WebConfig extends WebMvcConfigurerAdapter {

//    @Autowired
//    private SimpleCorsInterceptor simpleCorsInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(simpleCorsInterceptor);
    }
}
