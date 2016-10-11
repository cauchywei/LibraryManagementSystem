package xp.librarian.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author xp
 */
@Configuration
@Import({RepositoryConfig.class})
@ComponentScan(basePackages = {"xp.librarian.service"})
public class ServiceConfig {

}
