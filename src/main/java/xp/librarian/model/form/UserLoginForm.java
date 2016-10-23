package xp.librarian.model.form;

import java.io.*;

import lombok.Data;
import xp.librarian.model.dto.User;

/**
 * @author xp
 */
@Data
public class UserLoginForm implements Serializable {

    private static final long serialVersionUID = -5600232455702114345L;

    private String username;

    private String password;

    public User toDTO() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

}
