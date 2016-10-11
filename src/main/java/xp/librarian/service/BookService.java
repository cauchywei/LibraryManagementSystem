package xp.librarian.service;

import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xp.librarian.model.dto.BookDto;
import xp.librarian.model.form.BookSearchForm;
import xp.librarian.model.result.BookVM;
import xp.librarian.repository.BookDao;
import xp.librarian.repository.BorrowRecordDao;

/**
 * @author xp
 */
@Service
@Transactional
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BorrowRecordDao borrowRecordDao;

    public List<BookVM> search(BookSearchForm form) {
        List<BookDto> books = new LinkedList<>();
        books.add(bookDao.get(form.getIsbn()));
        books.addAll(bookDao.search(form.getIsbn(), form.getName()));
        return books.stream()
                .filter((e) -> e != null)
                .map((e) -> new BookVM().withBook(e))
                .collect(Collectors.toList());
    }

    public BookVM get(String isbn) {
        BookDto book = bookDao.get(isbn);
        return new BookVM().withBook(book);
    }

}
