package xp.librarian.model.result;

import java.io.*;
import java.util.*;

import lombok.Data;
import xp.librarian.model.dto.Role;
import xp.librarian.model.dto.User;

/**
 * @author xp
 */
@Data
public class UserProfileVM implements Serializable {

    private static final long serialVersionUID = -4849194255600852709L;

    private Integer id;

    private String username;

    private Role[] roles;

    private String name;

    private String avatarUrl;

    private Integer age;

    private String major;

    private String phone;

    private String email;

    private String remarks;

    private Long createTime;

    public UserProfileVM withUser(User user) {
        if (user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.roles = user.getRoles().toArray(new Role[0]);
            this.name = user.getName();
            this.avatarUrl = user.getAvatarUrl();
            this.age = user.getAge();
            this.major = user.getMajor();
            this.phone = user.getPhone();
            this.email = user.getEmail();
            this.remarks = user.getRemarks();
            this.createTime = Optional.ofNullable(user.getCreateTime()).map(Date::getTime).orElse(null);
        }
        return this;
    }

}
