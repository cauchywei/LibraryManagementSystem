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

    public int delete(String ISBN) {
        return bookMapper.delete(ISBN);
    }

    public BookDto get(String ISBN) {
        return bookMapper.select(ISBN);
    }

    public List<BookDto> gets(int page, int limits) {
        int offset = (page - 1) * limits;
        return bookMapper.selectList(offset, limits);
    }

    public Map<String, BookDto> gets(Collection<String> ISBNs) {
        List<BookDto> books = bookMapper.selectIN(ISBNs);
        return books.stream().collect(Collectors.toMap(BookDto::getISBN, e -> e));
    }

    public List<BookDto> search(String name) {
        return bookMapper.search(String.format("%%%s%%", name));
    }

    public int updateMargin(String ISBN, Integer delta) {
        return bookMapper.updateMargin(ISBN, delta);
    }

}
