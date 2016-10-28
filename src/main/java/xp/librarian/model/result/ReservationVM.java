package xp.librarian.model.result;

import java.io.*;
import java.time.*;
import java.util.*;

import lombok.Data;
import xp.librarian.model.dto.Book;
import xp.librarian.model.dto.BookTrace;
import xp.librarian.model.dto.Reservation;
import xp.librarian.model.dto.User;

/**
 * @author xp
 */
@Data
public class ReservationVM implements Serializable {

    private static final long serialVersionUID = 6309566730557914905L;

    private Integer id;

    private Integer userId;

    private UserVM user;

    private Integer traceId;

    private BookTraceVM trace;

    private Reservation.Status status;

    private Long applyingTime;

    private Long enabledTime;

    public ReservationVM withReservation(Reservation reservation) {
        if (reservation != null) {
            this.id = reservation.getId();
            this.userId = reservation.getUserId();
            this.traceId = reservation.getTraceId();
            this.status = reservation.getStatus();
            this.applyingTime = Optional.ofNullable(reservation.getApplyingTime()).map(Instant::toEpochMilli).orElse(null);
            this.enabledTime = Optional.ofNullable(reservation.getEnabledTime()).map(Instant::toEpochMilli).orElse(null);
        }
        return this;
    }

    public ReservationVM withUser(User user) {
        if (user != null) {
            this.user = new UserVM().withUser(user);
        }
        return this;
    }

    public ReservationVM withTrace(BookTrace trace, Book book) {
        if (trace != null) {
            this.trace = new BookTraceVM().withTrace(trace).withBook(book);
        }
        return this;
    }

}
