package xp.librarian.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import xp.librarian.config.ServiceConfig;
import xp.librarian.model.form.UserRegisterForm;
import xp.librarian.service.UserService;

/**
 * @author xp
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceConfig.class})
@ActiveProfiles({"test"})
@Transactional
public class UserRegisterTest {

    @Autowired
    private UserService userService;

    @Test
    public void blankFormTest() {
        userService.register(new UserRegisterForm());
    }

    @Test
    public void uploadAvatarTest() {
        UserRegisterForm form = new UserRegisterForm();
        form.setAvatar(new MockMultipartFile("avatar", "avatar.jpg", "image/jpeg", "bad access".getBytes()));
        userService.register(form);
    }

}
