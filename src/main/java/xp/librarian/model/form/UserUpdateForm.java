package xp.librarian.model.form;

import java.io.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import xp.librarian.model.dto.User;

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

    public User toDTO() {
        return new User()
                .setPassword(password)
                .setName(name)
                .setAge(age)
                .setMajor(major)
                .setPhone(phone)
                .setEmail(email)
                .setRemarks(remarks);
    }

}
