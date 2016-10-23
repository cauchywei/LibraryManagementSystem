package xp.librarian.service.reader;

import java.util.*;
import java.util.stream.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import xp.librarian.model.dto.Book;
import xp.librarian.model.dto.BookTrace;
import xp.librarian.model.dto.Lend;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.BookTraceVM;
import xp.librarian.repository.BookDao;
import xp.librarian.repository.BookTraceDao;
import xp.librarian.repository.LendDao;
import xp.librarian.repository.UserDao;

/**
 * @author xp
 */
@Service("readerBookTraceService")
@Transactional
public class BookTraceService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookTraceDao traceDao;

    @Autowired
    private LendDao lendDao;

    private BookTraceVM buildTraceVM(@NonNull BookTrace trace) {
        Book book = bookDao.get(trace.getIsbn(), true);
        if (book == null) {
            throw new ResourceNotFoundException("book not found.");
        }
        BookTraceVM vm = new BookTraceVM();
        vm.withTrace(trace).withBook(book);
        Lend lend = Optional.ofNullable(trace.getLendId()).map(lendDao::get).orElse(null);
        if (lend != null) {
            vm.withLend(lend, Optional.ofNullable(lend.getUserId())
                    .map(userId -> userDao.get(userId, true)).orElse(null));
        }
        return vm;
    }

    public List<BookTraceVM> getTraces(@NonNull String isbn,
                                       @Valid PagingForm paging) {
        Book book = bookDao.get(isbn, true);
        if (book == null) {
            throw new ResourceNotFoundException("book not found.");
        }
        BookTrace where = new BookTrace();
        where.setIsbn(isbn);
        List<BookTrace> traces = traceDao.gets(where, paging.getPage(), paging.getLimits(), true);
        return traces.stream()
                .filter(e -> e != null)
                .distinct()
                .map(this::buildTraceVM)
                .collect(Collectors.toList());
    }

    public BookTraceVM getTrace(@NonNull String isbn,
                                @NonNull Integer traceId) {
        BookTrace trace = traceDao.get(traceId, true);
        if (trace == null) {
            throw new ResourceNotFoundException("book trace not found.");
        }
        if (!trace.getIsbn().equals(isbn)) {
            throw new InputMismatchException("isbn not match.");
        }
        return buildTraceVM(trace);
    }

}
