package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.experimental.Accessors;
import xp.librarian.model.dto.User;

/**
 * @author xp
 */
@Data
@Accessors(chain = true)
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

    public User toDTO() {
        return new User()
                .setUsername(username)
                .setPassword(password)
                .setName(name)
                .setAge(age)
                .setMajor(major)
                .setPhone(phone)
                .setEmail(email)
                .setRemarks(remarks);
    }

}
