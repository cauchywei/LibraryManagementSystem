package xp.librarian.repository.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import xp.librarian.model.dto.UserRole;

/**
 * @author xp
 */
public interface UserRoleMapper {

    int insert(UserRole role);

    List<UserRole> select(@Param("userId") Integer userId);

}
