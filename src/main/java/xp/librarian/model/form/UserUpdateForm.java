package xp.librarian.model.form;

import java.io.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import xp.librarian.model.dto.UserDto;

/**
 * @author xp
 */
@Data
public class UserUpdateForm implements Serializable {

    private static final long serialVersionUID = 6341664064407339101L;

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
