package xp.librarian.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import xp.librarian.config.ServiceConfig;
import xp.librarian.model.form.UserRegisterForm;

/**
 * @author xp
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceConfig.class})
@Transactional
@ActiveProfiles("test")
public class UserServiceTest {

    @Test
    public void registerTest() {
    }

}
