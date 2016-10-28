package xp.librarian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

import xp.librarian.config.WebConfig;

/**
 * @author xp
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@Import({WebConfig.class})
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.info("LOG ON");
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

}
