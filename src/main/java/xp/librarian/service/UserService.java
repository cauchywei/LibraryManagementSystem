package xp.librarian.service;

import java.util.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import xp.librarian.model.context.*;
import xp.librarian.model.dto.Role;
import xp.librarian.model.dto.User;
import xp.librarian.model.form.UserLoginForm;
import xp.librarian.model.form.UserRegisterForm;
import xp.librarian.model.form.UserUpdateForm;
import xp.librarian.model.result.UserProfileVM;
import xp.librarian.repository.UserDao;
import xp.librarian.utils.TimeUtils;
import xp.librarian.utils.UploadUtils;

/**
 * @author xp
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private Validator validator;

    @Autowired
    private UserDao userDao;

    private UserProfileVM buildUserProfileVM(@NonNull User user) {
        return new UserProfileVM().withUser(user);
    }

    public UserProfileVM register(@Valid UserRegisterForm form) {
        Set<ConstraintViolation<UserRegisterForm>> vSet = validator.validate(form);
        if (!vSet.isEmpty()) {
            throw new ValidationException(vSet);
        }
        User where = new User().setUsername(form.getUsername());
        if (userDao.get(where, true) != null) {
            throw new BusinessException(ErrorCode.USER_EXISTS);
        }
        User user = form.toDTO()
                .setAvatarUrl(UploadUtils.upload(form.getAvatar()))
                .setStatus(User.Status.NORMAL)
                .setCreateTime(TimeUtils.now());
        if (0 == userDao.add(user)) {
            throw new PersistenceException("user insert failed.");
        }
        if (0 == userDao.addRole(user, Role.READER)) {
            throw new PersistenceException("user role insert failed.");
        }
        return buildUserProfileVM(user);
    }

    public UserProfileVM login(@Valid UserLoginForm form) {
        Set<ConstraintViolation<UserLoginForm>> vSet = validator.validate(form);
        if (!vSet.isEmpty()) {
            throw new ValidationException(vSet);
        }
        User where = form.toDTO();
        User user = userDao.get(where);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_LOGIN_FAIL);
        }
        return buildUserProfileVM(user);
    }

    public void logout(@NonNull AccountContext account) {
        // need to do nothing now.
    }

    public UserProfileVM getProfile(@NonNull AccountContext account) {
        User user = userDao.get(account.getId());
        if (user == null) {
            throw new ResourceNotFoundException("user not found.");
        }
        return buildUserProfileVM(user);
    }

    public UserProfileVM setProfile(@NonNull AccountContext account, @Valid UserUpdateForm form) {
        Set<ConstraintViolation<UserUpdateForm>> vSet = validator.validate(form);
        if (!vSet.isEmpty()) {
            throw new ValidationException(vSet);
        }
        User where = account.toDTO();
        User set = form.toDTO()
                .setAvatarUrl(UploadUtils.upload(form.getAvatar()));
        if (0 == userDao.update(where, set)) {
            throw new PersistenceException("user update failed.");
        }
        return buildUserProfileVM(userDao.get(account.getId()));
    }

}
