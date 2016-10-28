package xp.librarian.service.admin;

import java.time.*;
import java.util.*;
import java.util.stream.*;

import javax.validation.Valid;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import xp.librarian.model.context.BusinessException;
import xp.librarian.model.context.ErrorCode;
import xp.librarian.model.dto.Book;
import xp.librarian.model.form.BookAddForm;
import xp.librarian.model.form.BookUpdateForm;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.BookVM;
import xp.librarian.repository.BookDao;

/**
 * @author xp
 */
@Service("adminBookService")
@Transactional
public class BookService {

    @Autowired
    private BookDao bookDao;

    private BookVM buildBookVM(@NonNull Book book) {
        return new BookVM().withBook(book);
    }

    public BookVM addBook(@Valid BookAddForm form) {
        Book book = form.toDTO();
        if (book.getStatus() == null) {
            book.setStatus(Book.Status.NORMAL);
        }
        book.setCreateTime(Instant.now());
        if (0 == bookDao.add(book)) {
            throw new PersistenceException("book insert failed.");
        }
        return buildBookVM(book);
    }

    public BookVM updateBook(@Valid BookUpdateForm form) {
        Book book = bookDao.get(form.getIsbn(), true);
        if (book == null) {
            throw new ResourceNotFoundException("book not found.");
        }
        Book where = new Book();
        where.setIsbn(book.getIsbn());
        where.setStatus(book.getStatus());
        Book set = form.toDTO();
        if (0 == bookDao.update(where, set)) {
            throw new PersistenceException("book update failed.");
        }
        return buildBookVM(bookDao.get(book.getIsbn(), true));
    }

    public void deleteBook(@NonNull String isbn) {
        Book book = bookDao.get(isbn, true);
        if (book == null) {
            throw new ResourceNotFoundException("book not found.");
        }
        if (!Book.Status.NORMAL.equals(book.getStatus())) {
            throw new BusinessException(ErrorCode.ADMIN_BOOK_STATUS_MISMATCH);
        }
        Book where = new Book();
        where.setIsbn(isbn);
        where.setStatus(Book.Status.NORMAL);
        Book set = new Book();
        set.setStatus(Book.Status.DELETED);
        if (0 == bookDao.update(where, set)) {
            throw new PersistenceException("book update failed.");
        }
        //TODO DELETE all traces
    }

    public List<BookVM> getBooks(@Valid PagingForm paging) {
        List<Book> books = bookDao.gets(new Book(), paging.getPage(), paging.getLimits(), true);
        return books.stream()
                .filter((e) -> e != null)
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
