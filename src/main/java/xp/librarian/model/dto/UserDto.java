package xp.librarian.model.dto;

import java.io.*;
import java.util.*;

import lombok.Data;

/**
 * @author xp
 */
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -3210175692337394666L;

    public enum Role {
        ADMIN("ADMIN"),
        READER("READER");

        private String value;

        Role(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Status {
        NORMAL("NORMAL"),
        FROZEN("FROZEN"),
        DELETE("DELETE");

        private String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private Integer id;

    private String username;

    private String password;

    private Role role;

    private Status status;

    private String name;

    private String avatarUrl;

    private Integer age;

    private String major;

    private String phone;

    private String email;

    private String remarks;

    private Date createTime;

}
