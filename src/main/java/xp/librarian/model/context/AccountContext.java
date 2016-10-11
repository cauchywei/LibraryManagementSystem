package xp.librarian.model.context;

import java.io.*;

import lombok.Data;
import xp.librarian.model.dto.UserDto;

/**
 * @author xp
 */
@Data
public class AccountContext implements Serializable {

    private static final long serialVersionUID = 25302623067800609L;

    private Integer id;

    private UserDto.Role role;

    private UserDto.Status status;

    public static AccountContext fromDTO(UserDto user) {
        AccountContext account = new AccountContext();
        account.id = user.getId();
        account.role = user.getRole();
        account.status = user.getStatus();
        return account;
    }

}
