package xp.librarian.repository.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import xp.librarian.model.dto.Lend;

/**
 * @author xp
 */
@Mapper
public interface LendMapper {

    int insert(Lend lend);

    int update(@Param("where") Lend where,
               @Param("set") Lend set);

    List<Lend> select(@Param("where") Lend where,
                      @Param("offset") Integer offset,
                      @Param("limits") Integer limits);

}
