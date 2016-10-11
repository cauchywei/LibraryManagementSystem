package xp.librarian.service.admin;

import java.util.*;
import java.util.stream.*;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xp.librarian.model.dto.BookDto;
import xp.librarian.model.form.BookAddForm;
import xp.librarian.model.form.BookUpdateForm;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.BookVM;
import xp.librarian.repository.BookDao;

/**
 * @author xp
 */
@Service("adminBookService")
public class BookService {

    @Autowired
    private BookDao bookDao;

    public BookVM add(BookAddForm form) {
        BookDto book = form.toDTO();
        book.setStatus(BookDto.Status.NORMAL);
        book.setCreateTime(new Date(System.currentTimeMillis()));
        if (1 != bookDao.add(book)) {
            throw new PersistenceException();
        }
        return new BookVM().withBook(book);
    }

    public List<BookVM> getList(PagingForm paging) {
        List<BookDto> books = bookDao.gets(paging.getPage(), paging.getLimits());
        return books.stream()
                .filter((e) -> e != null)
                .map((e) -> new BookVM().withBook(e))
                .collect(Collectors.toList());
    }

    public BookVM get(String ISBN) {
        BookDto book = bookDao.get(ISBN);
        return new BookVM().withBook(book);
    }

    public BookVM update(BookUpdateForm form) {
        BookDto book = form.toDTO();
        if (1 != bookDao.update(book)) {
            throw new PersistenceException();
        }
        book = bookDao.get(book.getIsbn());
        return new BookVM().withBook(book);
    }

    public boolean delete(String isbn) {
        return 1 == bookDao.delete(isbn);
    }

}
