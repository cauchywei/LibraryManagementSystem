package xp.librarian.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import xp.librarian.controller.BaseController;
import xp.librarian.model.form.PagingForm;
import xp.librarian.service.admin.UserService;

/**
 * @author xp
 */
@Controller("adminUserController")
@RequestMapping(value = "/admin/users/")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity getUserList(PagingForm paging) {
        return renderForEntities(userService.getList(paging));
    }

    @GetMapping("{userId}/")
    public ResponseEntity getUser(@PathVariable Integer userId) {
        return renderForEntity(userService.get(userId));
    }

    @PostMapping("{userId}/freeze")
    public ResponseEntity freezeUser(@PathVariable Integer userId) {
        return renderForAction(userService.freeze(userId));
    }

    @PostMapping("{userId}/unfreeze")
    public ResponseEntity unfreezeUser(@PathVariable Integer userId) {
        return renderForAction(userService.unfreeze(userId));
    }

    @PostMapping("{userId}/delete")
    public ResponseEntity deleteUser(@PathVariable Integer userId) {
        return renderForAction(userService.delete(userId));
    }

}
