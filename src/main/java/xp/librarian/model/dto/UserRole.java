package xp.librarian.model.dto;

import java.io.*;
import java.util.*;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xp
 */
@Getter
@Setter
public class UserRole implements Serializable {

    private static final long serialVersionUID = -298349867748056048L;

    public Integer userId;

    public Role role;

    public Date createTime;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UserRole)) return false;
        UserRole userRole = (UserRole) object;
        return Objects.equals(userId, userRole.userId) &&
                role == userRole.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, role);
    }
}
