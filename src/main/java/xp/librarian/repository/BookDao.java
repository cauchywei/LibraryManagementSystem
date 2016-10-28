package xp.librarian.repository;

import java.util.*;

import xp.librarian.model.dto.Book;

/**
 * @author xp
 */
public interface BookDao {

    int add(Book book);

    int update(Book where, Book set);

    Book get(String isbn);

    Book get(String isbn, boolean force);

    List<Book> gets(Book where, Integer page, Integer limits);

    List<Book> gets(Book where, Integer page, Integer limits, boolean force);

    List<Book> search(Book book, Integer page, Integer limits);

    List<Book> search(Book book, Integer page, Integer limits, boolean force);

}
