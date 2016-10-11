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

    int delete(@Param("isbn") String isbn);

    BookDto select(@Param("isbn") String isbn);

    List<BookDto> selectList(@Param("offset") int offset, @Param("limits") int limits);

    List<BookDto> selectIN(@Param("isbns") Collection<String> isbns);

    List<BookDto> search(@Param("isbn") String isbn, @Param("name") String name);

    int updateMargin(@Param("isbn") String isbn, @Param("delta") Integer delta);

}
