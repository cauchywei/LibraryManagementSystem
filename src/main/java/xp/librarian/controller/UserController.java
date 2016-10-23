package xp.librarian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import xp.librarian.model.context.AccountContext;
import xp.librarian.model.form.UserLoginForm;
import xp.librarian.model.form.UserRegisterForm;
import xp.librarian.model.form.UserUpdateForm;
import xp.librarian.model.result.UserProfileVM;
import xp.librarian.service.UserService;
import xp.librarian.utils.LoginUtils;

/**
 * @author xp
 */
@Api(
        description = "Users 个人中心"
)
@RestController
@RequestMapping(value = "/users/")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public Object register(UserRegisterForm form) {
        UserProfileVM vm = userService.register(form);
        LoginUtils.login(vm.getId());
        return renderForEntity(vm);
    }

    @PostMapping("login")
    public Object login(UserLoginForm form) {
        UserProfileVM vm = userService.login(form);
        LoginUtils.login(vm.getId());
        return renderForEntity(vm);
    }

    @PostMapping("logout")
    public Object logout() {
        AccountContext account = getAccount(false);
        if (account != null) {
            userService.logout(account);
        }
        LoginUtils.logout();
        return renderForAction(true);
    }

    @GetMapping("self/")
    public Object getSelfProfile() {
        return renderForEntity(userService.getProfile(getAccount()));
    }

    @PostMapping("self/")
    public Object updateSelfProfile(UserUpdateForm form) {
        return renderForEntity(userService.setProfile(getAccount(), form));
    }

}
