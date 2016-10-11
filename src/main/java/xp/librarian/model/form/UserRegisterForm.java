package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import xp.librarian.model.dto.UserDto;

/**
 * @author xp
 */
@Data
public class UserRegisterForm implements Serializable {

    private static final long serialVersionUID = 4360309392722354036L;

    @NotNull
    private String username;

    private String password;

    private String name;

    private MultipartFile avatar;

    private Integer age;

    private String major;

    private String phone;

    private String email;

    private String remarks;

    public UserDto toDTO() {
        UserDto user = new UserDto();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setAge(age);
        user.setMajor(major);
        user.setPhone(phone);
        user.setEmail(email);
        user.setRemarks(remarks);
        return user;
    }

}
