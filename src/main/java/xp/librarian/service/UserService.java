package xp.librarian.service;

import java.util.*;

import javax.validation.Valid;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xp.librarian.model.context.AccountContext;
import xp.librarian.model.dto.UserDto;
import xp.librarian.model.form.UserLoginForm;
import xp.librarian.model.form.UserRegisterForm;
import xp.librarian.model.form.UserUpdateForm;
import xp.librarian.model.result.UserProfileVM;
import xp.librarian.repository.UserDao;
import xp.librarian.utils.BusinessException;
import xp.librarian.utils.LoginUtils;
import xp.librarian.utils.UploadUtils;

/**
 * @author xp
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserProfileVM register(@Valid UserRegisterForm form) {
        UserDto user = form.toDTO();
        user.setAvatarUrl(UploadUtils.upload(form.getAvatar()));
        user.setRole(UserDto.Role.READER);
        user.setStatus(UserDto.Status.NORMAL);
        user.setCreateTime(new Date(System.currentTimeMillis()));
        if (1 != userDao.add(user)) {
            throw new PersistenceException();
        }
        LoginUtils.login(user.getId());
        return new UserProfileVM().withUser(user);
    }

    public UserProfileVM login(@Valid UserLoginForm form) {
        UserDto user = userDao.getForLogin(form.getUsername(), form.getPassword());
        if (user == null) {
            throw new BusinessException("user.loginFailed");
        }
        LoginUtils.login(user.getId());
        return new UserProfileVM().withUser(user);
    }

    public boolean logout() {
        return LoginUtils.logout();
    }

    public UserProfileVM getProfile(AccountContext account) {
        UserDto user = userDao.get(account.getId());
        return new UserProfileVM().withUser(user);
    }

    public UserProfileVM setProfile(AccountContext account, @Valid UserUpdateForm form) {
        UserDto user = form.toDTO();
        user.setId(account.getId());
        user.setAvatarUrl(UploadUtils.upload(form.getAvatar()));
        user = userDao.get(user.getId());
        return new UserProfileVM().withUser(user);
    }

}
