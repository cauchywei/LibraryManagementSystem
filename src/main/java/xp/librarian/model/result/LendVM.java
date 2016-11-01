package xp.librarian.model.result;

import java.io.*;
import java.time.*;
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
public class LendVM implements Serializable {

    private static final long serialVersionUID = 6784313847752988388L;

    private Integer id;

    private Integer userId;

    private UserVM user;

    private Integer traceId;

    private BookTraceVM trace;

    private Lend.Status status;

    private Integer renew;

    private Long applyingTime;

    private Long appointedTime;

    private Long expiredTime;

    public LendVM withLend(Lend lend) {
        if (lend != null) {
            this.id = lend.getId();
            this.userId = lend.getUserId();
            this.traceId = lend.getTraceId();
            this.status = lend.getStatus();
            this.renew = lend.getRenew();
            this.applyingTime = Optional.ofNullable(lend.getApplyingTime()).map(Instant::toEpochMilli).orElse(null);
            this.appointedTime = Optional.ofNullable(lend.getAppointedTime()).map(Instant::toEpochMilli).orElse(null);
            this.expiredTime = Optional.ofNullable(lend.getExpiredTime()).map(Instant::toEpochMilli).orElse(null);
        }
        return this;
    }

    public LendVM withUser(User user) {
        if (user != null) {
            this.user = new UserVM().withUser(user);
        }
        return this;
    }

    public LendVM withTrace(BookTrace trace, Book book) {
        if (trace != null) {
            this.trace = new BookTraceVM().withTrace(trace).withBook(book);
        }
        return this;
    }

}
