package xp.librarian.service.reader;

import java.time.*;
import java.util.*;
import java.util.stream.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import xp.librarian.model.dto.Book;
import xp.librarian.model.form.BookSearchForm;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.BookVM;
import xp.librarian.repository.BookDao;

/**
 * @author xp
 */
@Service("readerBookService")
@Transactional
public class BookService {

    @Autowired
    private BookDao bookDao;

    private BookVM buildBookVM(@NonNull Book book) {
        return new BookVM().withBook(book);
    }

    public List<BookVM> search(@Valid BookSearchForm form,
                               @Valid PagingForm paging) {
        Book book = new Book();
        book.setIsbn(form.getIsbn());
        book.setName(form.getName());
        book.setStatus(Book.Status.NORMAL);
        List<Book> books = bookDao.search(book, paging.getPage(), paging.getLimits(), true);
        return books.stream()
                .filter(e -> e != null)
                .distinct()
                .map(this::buildBookVM)
                .collect(Collectors.toList());
    }

    public BookVM getBook(@NonNull String isbn) {
        Book book = bookDao.get(isbn, true);
        if (book == null) {
            throw new ResourceNotFoundException("book not found.");
        }
        return buildBookVM(book);
    }

}
