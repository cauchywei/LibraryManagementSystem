package xp.librarian.repository.impl;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import xp.librarian.model.dto.Book;
import xp.librarian.repository.BookDao;
import xp.librarian.repository.mapper.BookMapper;

/**
 * @author xp
 */
@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookMapper bookMapper;

    private static final Function<Boolean, Predicate<Book>> forceFilter =
            force -> e -> force || !Book.Status.DELETED.equals(e.getStatus());

    public int add(@NonNull Book book) {
        return bookMapper.insert(book);
    }

    public int update(@NonNull Book where,
                      @NonNull Book set) {
        return bookMapper.update(where, set);
    }

    public Book get(@NonNull String isbn) {
        return get(isbn, false);
    }

    public Book get(@NonNull String isbn, boolean force) {
        Book where = new Book();
        where.setIsbn(isbn);
        return gets(where, 1, 1, force).stream()
                .findFirst().orElse(null);
    }

    public List<Book> gets(@NonNull Book where,
                           @NonNull Integer page,
                           @NonNull Integer limits) {
        return gets(where, page, limits, false);
    }

    public List<Book> gets(@NonNull Book where,
                           @NonNull Integer page,
                           @NonNull Integer limits,
                           boolean force) {
        int offset = (page - 1) * limits;
        return bookMapper.select(where, offset, limits).stream()
                .filter(forceFilter.apply(force))
                .collect(Collectors.toList());
    }

    public List<Book> search(@NonNull Book book,
                             @NonNull Integer page,
                             @NonNull Integer limits) {
        return search(book, page, limits, false);
    }

    public List<Book> search(@NonNull Book book,
                             @NonNull Integer page,
                             @NonNull Integer limits,
                             boolean force) {
        Integer offset = (page - 1) * limits;
        return bookMapper.search(book, offset, limits).stream()
                .filter(forceFilter.apply(force))
                .collect(Collectors.toList());
    }

}
