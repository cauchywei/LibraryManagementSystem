package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
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

    @Length(max = 50)
    @Email
    private String email;

    @Length(max = 65535)
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
