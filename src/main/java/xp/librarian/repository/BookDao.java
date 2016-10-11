package xp.librarian.repository;

import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xp.librarian.model.dto.BookDto;
import xp.librarian.repository.mapper.BookMapper;

/**
 * @author xp
 */
@Repository
public class BookDao {

    @Autowired
    private BookMapper bookMapper;

    public int add(BookDto dto) {
        return bookMapper.insert(dto);
    }

    public int update(BookDto dto) {
        return bookMapper.update(dto);
    }

    public int delete(String isbn) {
        return bookMapper.delete(isbn);
    }

    public BookDto get(String isbn) {
        return bookMapper.select(isbn);
    }

    public List<BookDto> gets(int page, int limits) {
        int offset = (page - 1) * limits;
        return bookMapper.selectList(offset, limits);
    }

    public Map<String, BookDto> gets(Collection<String> isbns) {
        if (isbns.size() == 0) {
            return Collections.emptyMap();
        }
        List<BookDto> books = bookMapper.selectIN(isbns);
        return books.stream().collect(Collectors.toMap(BookDto::getIsbn, e -> e));
    }

    public List<BookDto> search(String isbn, String name) {
        return bookMapper.search(isbn, name);
    }

    public int updateMargin(String isbn, Integer delta) {
        return bookMapper.updateMargin(isbn, delta);
    }

}
