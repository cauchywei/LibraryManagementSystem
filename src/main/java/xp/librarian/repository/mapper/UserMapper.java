package xp.librarian.repository.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import xp.librarian.model.dto.UserDto;

/**
 * @author xp
 */
@Mapper
public interface UserMapper {

    int insert(UserDto dto);

    UserDto selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    UserDto select(@Param("id") Integer id);

    List<UserDto> selectList(@Param("offset") int offset, @Param("limits") int limits);

    int update(UserDto dto);

    int updateStatus(@Param("userId") Integer userId,
                     @Param("oldStatus") UserDto.Status oldStatus,
                     @Param("newStatus") UserDto.Status newStatus);

}
