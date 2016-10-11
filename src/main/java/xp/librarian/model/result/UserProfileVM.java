package xp.librarian.model.result;

import java.io.*;
import java.util.*;

import lombok.Data;
import xp.librarian.model.dto.UserDto;

/**
 * @author xp
 */
@Data
public class UserProfileVM implements Serializable {

    private static final long serialVersionUID = -4849194255600852709L;

    private Integer id;

    private String username;

    private UserDto.Role role;

    private String name;

    private String avatarUrl;

    private Integer age;

    private String major;

    private String phone;

    private String email;

    private String remarks;

    private Date createTime;

    public UserProfileVM withUser(UserDto user) {
        if (user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
            this.name = user.getName();
            this.avatarUrl = user.getAvatarUrl();
            this.age = user.getAge();
            this.major = user.getMajor();
            this.phone = user.getPhone();
            this.email = user.getEmail();
            this.remarks = user.getRemarks();
            this.createTime = user.getCreateTime();
        }
        return this;
    }

}
