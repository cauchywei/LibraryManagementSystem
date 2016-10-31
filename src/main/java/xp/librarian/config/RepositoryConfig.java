package xp.librarian.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author xp
 */
@Configuration
@Import({BootConfig.class})
@ComponentScan(basePackages = {"xp.librarian.repository.impl"})
@MapperScan(basePackages = {"xp.librarian.repository.mapper"})
public class RepositoryConfig {

}
