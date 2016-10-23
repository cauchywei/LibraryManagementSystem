package xp.librarian.model.result;

import java.io.*;
import java.util.*;

import lombok.Data;
import xp.librarian.model.dto.Book;
import xp.librarian.model.dto.BookTrace;
import xp.librarian.model.dto.Lend;
import xp.librarian.model.dto.User;

/**
 * @author xp
 */
@Data
public class BookTraceVM implements Serializable {

    private static final long serialVersionUID = -7116954866052993964L;

    private Integer id;

    private String isbn;

    private BookVM book;

    private BookTrace.Status status;

    private String location;

    private Integer lendId;

    private LendVM lend;

    private Long createTime;

    public BookTraceVM withTrace(BookTrace trace) {
        if (trace != null) {
            this.id = trace.getId();
            this.isbn = trace.getIsbn();
            this.status = trace.getStatus();
            this.location = trace.getLocation();
            this.lendId = trace.getLendId();
            this.createTime = Optional.ofNullable(trace.getCreateTime()).map(Date::getTime).orElse(null);
        }
        return this;
    }

    public BookTraceVM withBook(Book book) {
        if (book != null) {
            this.book = new BookVM().withBook(book);
        }
        return this;
    }

    public BookTraceVM withLend(Lend lend, User user) {
        if (lend != null) {
            this.lend = new LendVM().withLend(lend).withUser(user);
        }
        return this;
    }

}
