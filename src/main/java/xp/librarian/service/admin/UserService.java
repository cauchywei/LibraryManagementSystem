package xp.librarian.service.admin;

import java.util.*;
import java.util.stream.*;

import javax.validation.Valid;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import xp.librarian.model.context.ResourceNotFoundException;
import xp.librarian.model.dto.User;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.UserProfileVM;
import xp.librarian.repository.UserDao;

/**
 * @author xp
 */
@Service("adminUserService")
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    private UserProfileVM buildUserProfileVM(@NonNull User user) {
        return new UserProfileVM().withUser(user);
    }

    public List<UserProfileVM> getUsers(@Valid PagingForm paging) {
        List<User> users = userDao.gets(new User(), paging.getPage(), paging.getLimits(), true);
        return users.stream()
                .filter(e -> e != null)
                .distinct()
                .map(this::buildUserProfileVM)
                .collect(Collectors.toList());
    }

    public UserProfileVM getUser(@NonNull Integer userId) {
        User user = userDao.get(userId, true);
        if (user == null) {
            throw new ResourceNotFoundException("user not found.");
        }
        return buildUserProfileVM(user);
    }

    private void setStatus(Integer userId, User.Status oldValue, User.Status newValue) {
        User where = new User();
        where.setId(userId);
        where.setStatus(oldValue);
        User set = new User();
        set.setStatus(newValue);
        if (0 == userDao.update(where, set)) {
            throw new PersistenceException("user update failed.");
        }
    }

    private void setStatus(Integer userId, User.Status newValue) {
        setStatus(userId, null, newValue);
    }

    public void freeze(@NonNull Integer userId) {
        setStatus(userId, User.Status.NORMAL, User.Status.FROZEN);
    }

    public void unfreeze(@NonNull Integer userId) {
        setStatus(userId, User.Status.FROZEN, User.Status.NORMAL);
    }

    public void delete(@NonNull Integer userId) {
        setStatus(userId, User.Status.DELETED);
    }

}
