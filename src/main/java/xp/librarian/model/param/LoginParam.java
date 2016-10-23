package xp.librarian.model.param;

import java.io.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xp.librarian.model.form.UserLoginForm;

/**
 * @author xp
 */
@AllArgsConstructor
@Getter
public class LoginParam implements Serializable {

    private static final long serialVersionUID = -2973526119550958438L;

    private String username;

    private String password;

    public static LoginParam from(UserLoginForm form) {
        return new LoginParam(form.getUsername(), form.getPassword());
    }

}
