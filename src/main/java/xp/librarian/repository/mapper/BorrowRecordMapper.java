package xp.librarian.repository.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import xp.librarian.model.dto.BorrowRecordDto;

/**
 * @author xp
 */
@Mapper
public interface BorrowRecordMapper {

    int insert(BorrowRecordDto dto);

    BorrowRecordDto select(@Param("userId") Integer userId,
                           @Param("isbn") String ISBN,
                           @Param("status") BorrowRecordDto.Status status);

    List<BorrowRecordDto> selectList(@Param("userId") Integer userId, @Param("offset") int offset, @Param("limits") int limits);

    int update(BorrowRecordDto dto);

}
