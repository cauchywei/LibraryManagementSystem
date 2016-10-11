package xp.librarian.repository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xp.librarian.model.dto.UserDto;
import xp.librarian.repository.mapper.UserMapper;

/**
 * @author xp
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public int add(UserDto dto) {
        return userMapper.insert(dto);
    }

    public int update(UserDto dto) {
        return userMapper.update(dto);
    }

    public int updateStatus(Integer userId, UserDto.Status oldStatus, UserDto.Status newStatus) {
        return userMapper.updateStatus(userId, oldStatus, newStatus);
    }

    public UserDto get(Integer userId) {
        return userMapper.select(userId);
    }

    public List<UserDto> gets(int page, int limits) {
        int offset = (page - 1) * limits;
        return userMapper.selectList(offset, limits);
    }

    public UserDto getForLogin(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password);
    }

}
