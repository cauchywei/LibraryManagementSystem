package xp.librarian.model.form;

import java.io.*;

import lombok.Data;

/**
 * @author xp
 */
@Data
public class UserLoginForm implements Serializable {

    private static final long serialVersionUID = -5600232455702114345L;

    private String username;

    private String password;

}
