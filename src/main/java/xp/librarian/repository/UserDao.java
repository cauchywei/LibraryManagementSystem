package xp.librarian.repository;

import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import xp.librarian.model.dto.Role;
import xp.librarian.model.dto.User;
import xp.librarian.model.dto.UserRole;
import xp.librarian.repository.mapper.UserMapper;
import xp.librarian.repository.mapper.UserRoleMapper;

/**
 * @author xp
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper roleMapper;

    private static final Function<Boolean, Predicate<User>> forceFilter =
            force -> e -> force || !User.Status.DELETED.equals(e.getStatus());

    public int add(@NonNull User user) {
        int result = userMapper.insert(user);
        if (result > 0) {
            UserRole role = new UserRole();
            role.setUserId(user.getId());
            role.setRole(Role.READER);
            role.setCreateTime(Date.from(Instant.now()));
            int result1 = roleMapper.insert(role);
            if (result1 > 0) {
                user.setRoles(Collections.singleton(Role.READER));
                return 1;
            }
        }
        return 0;
    }

    public int update(@NonNull User where,
                      @NonNull User set) {
        return userMapper.update(where, set);
    }

    public User get(@NonNull Integer userId) {
        return get(userId, false);
    }

    public User get(@NonNull Integer userId,
                    boolean force) {
        User where = new User();
        where.setId(userId);
        return get(where, force);
    }

    public User get(@NonNull User where) {
        return get(where, false);
    }

    public User get(@NonNull User where,
                    boolean force) {
        return gets(where, 1, 1, force).stream()
                .findFirst().orElse(null);
    }

    public List<User> gets(@NonNull User where,
                           @NonNull Integer page,
                           @NonNull Integer limits) {
        return gets(where, page, limits, false);
    }

    public List<User> gets(@NonNull User where,
                           @NonNull Integer page,
                           @NonNull Integer limits,
                           boolean force) {
        int offset = (page - 1) * limits;
        return userMapper.select(where, offset, limits).stream()
                .filter(forceFilter.apply(force))
                .map(e -> {
                    List<UserRole> roles = roleMapper.select(e.getId());
                    e.setRoles(roles.stream().map(UserRole::getRole).collect(Collectors.toSet()));
                    return e;
                })
                .collect(Collectors.toList());
    }

}
