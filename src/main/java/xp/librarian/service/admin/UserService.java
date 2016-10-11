package xp.librarian.service.admin;

import java.util.*;
import java.util.stream.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xp.librarian.model.context.AccountContext;
import xp.librarian.model.dto.UserDto;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.UserProfileVM;
import xp.librarian.repository.UserDao;

/**
 * @author xp
 */
@Service("adminUserService")
@Transactional
public class UserService {

    //TODO 检查管理员权限

    @Autowired
    private UserDao userDao;

    public List<UserProfileVM> getList(PagingForm paging) {
        List<UserDto> users = userDao.gets(paging.getPage(), paging.getLimits());
        return users.stream()
                .filter((e) -> e != null)
                .map((e) -> new UserProfileVM().withUser(e))
                .collect(Collectors.toList());
    }

    public UserProfileVM get(Integer userId) {
        UserDto user = userDao.get(userId);
        return new UserProfileVM().withUser(user);
    }

    private boolean setStatus(Integer userId, UserDto.Status oldStatus, UserDto.Status newStatus) {
        return 1 == userDao.updateStatus(userId, oldStatus, newStatus);
    }

    private boolean setStatus(Integer userId, UserDto.Status newStatus) {
        UserDto user = new UserDto();
        user.setId(userId);
        user.setStatus(newStatus);
        return 1 == userDao.update(user);
    }

    public boolean freeze(Integer userId) {
        return setStatus(userId, UserDto.Status.NORMAL, UserDto.Status.FROZEN);
    }

    public boolean unfreeze(Integer userId) {
        return setStatus(userId, UserDto.Status.FROZEN, UserDto.Status.NORMAL);
    }

    public boolean delete(Integer userId) {
        return setStatus(userId, UserDto.Status.DELETE);
    }

}
