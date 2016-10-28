package xp.librarian.model.dto;

import java.io.*;
import java.time.*;
import java.util.*;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author xp
 */
@Getter
@Setter
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = -3210175692337394666L;

    private Integer id;

    private String username;

    private String password;

    private transient Set<Role> roles;

    public enum Status {
        NORMAL,
        FROZEN,
        DELETED,
        ;
    }

    public Status status;

    public String name;

    public String avatarUrl;

    public Integer age;

    public String major;

    public String phone;

    public String email;

    public String remarks;

    public Instant createTime;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User)) return false;
        User user = (User) object;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
