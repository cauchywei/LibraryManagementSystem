package xp.librarian.repository;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import xp.librarian.model.dto.User;
import xp.librarian.model.param.LoginParam;
import xp.librarian.repository.mapper.UserMapper;

/**
 * @author xp
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    private static final Function<Boolean, Predicate<User>> forceFilter =
            force -> e -> force || !User.Status.DELETED.equals(e.getStatus());

    public int add(@NonNull User user) {
        return userMapper.insert(user);
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
                .collect(Collectors.toList());
    }

}
