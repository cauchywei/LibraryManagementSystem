package xp.librarian.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xp
 */
@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"xp.librarian.repository.impl"})
@MapperScan(basePackages = {"xp.librarian.repository.mapper"})
public class RepositoryConfig {

}
