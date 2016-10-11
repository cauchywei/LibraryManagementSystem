package xp.librarian.repository.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import xp.librarian.model.dto.BookDto;

/**
 * @author xp
 */
@Mapper
public interface BookMapper {

    int insert(BookDto dto);

    int update(BookDto dto);

    int delete(@Param("ISBN") String ISBN);

    BookDto select(@Param("ISBN") String ISBN);

    List<BookDto> selectList(@Param("offset") int offset, @Param("limits") int limits);

    List<BookDto> selectIN(@Param("ISBNs") Collection<String> ISBNs);

    List<BookDto> search(@Param("name") String name);

    int updateMargin(@Param("ISBN") String ISBN, @Param("delta") Integer delta);

}
