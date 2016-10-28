package xp.librarian.repository;

import java.util.*;

import xp.librarian.model.dto.Role;
import xp.librarian.model.dto.User;

/**
 * @author xp
 */
public interface UserDao {

    int add(User user);

    int update(User where, User set);

    User get(Integer userId);

    User get(Integer userId, boolean force);

    User get(User where);

    User get(User where, boolean force);

    List<User> gets(User where, Integer page, Integer limits);

    List<User> gets(User where, Integer page, Integer limits, boolean force);

    int addRole(User user, Role role);

    int removeRole(User user, Role role);

}
