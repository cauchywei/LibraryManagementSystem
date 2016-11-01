package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
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

    @NotBlank
    @Length(min = 3, max = 50)
    @Pattern(regexp = "[0-9A-Za-z_]+")
    private String username;

    @NotBlank
    private String password;

    @Length(max = 50)
    private String name;

    private MultipartFile avatar;

    @Range(min = 0L, max = 120L)
    private Integer age;

    @Length(max = 50)
    private String major;

    @Length(max = 20)
    @Pattern(regexp = "[+0-9\\-]+")
    private String phone;

    @Email
    @Length(max = 50)
    private String email;

    @Length(max = 65535)
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
