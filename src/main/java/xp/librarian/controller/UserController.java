package xp.librarian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import xp.librarian.model.form.PagingForm;
import xp.librarian.model.form.UserLoginForm;
import xp.librarian.model.form.UserRegisterForm;
import xp.librarian.model.form.UserUpdateForm;
import xp.librarian.service.BorrowService;
import xp.librarian.service.UserService;

/**
 * @author xp
 */
@RestController
@RequestMapping(value = "/users/")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowService borrowService;

    @PostMapping("register")
    public ResponseEntity register(UserRegisterForm form) {
        return renderForEntity(userService.register(form));
    }

    @PostMapping("login")
    public ResponseEntity login(UserLoginForm form) {
        return renderForEntity(userService.login(form));
    }

    @PostMapping("logout")
    public ResponseEntity logout() {
        return renderForAction(userService.logout());
    }

    @GetMapping("self/")
    public ResponseEntity getSelfProfile() {
        return renderForEntity(userService.getProfile(getAccount()));
    }

    @PostMapping("self/")
    public ResponseEntity updateSelfProfile(UserUpdateForm form) {
        return renderForEntity(userService.setProfile(getAccount(), form));
    }

    @GetMapping("self/records/")
    public ResponseEntity getBorrowRecords(PagingForm paging) {
        return renderForEntities(borrowService.getRecords(getAccount(), paging));
    }

}
