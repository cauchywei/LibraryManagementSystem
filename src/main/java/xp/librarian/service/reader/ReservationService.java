package xp.librarian.service.reader;

import java.time.*;
import java.util.*;
import java.util.stream.*;

import javax.validation.Valid;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import xp.librarian.model.context.AccountContext;
import xp.librarian.model.context.BusinessException;
import xp.librarian.model.context.ErrorCode;
import xp.librarian.model.dto.*;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.form.ReservationListForm;
import xp.librarian.model.form.ReserveBookForm;
import xp.librarian.model.result.ReservationVM;
import xp.librarian.repository.*;

/**
 * @author xp
 */
@Service("readerReservationService")
@Transactional
public class ReservationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookTraceDao traceDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private RecordDao recordDao;

    private ReservationVM buildReservationVM(@NonNull Reservation reservation) {
        User user = userDao.get(reservation.getUserId(), true);
        if (user == null) {
            throw new ResourceNotFoundException("user not found.");
        }
        BookTrace trace = traceDao.get(reservation.getTraceId(), true);
        if (trace == null) {
            throw new ResourceNotFoundException("book trace not found.");
        }
        Book book = bookDao.get(trace.getIsbn(), true);
        if (book == null) {
            throw new ResourceNotFoundException("book not found.");
        }
        return new ReservationVM().withReservation(reservation).withUser(user).withTrace(trace, book);
    }

    public ReservationVM reserveBook(@NonNull AccountContext account,
                                     @Valid ReserveBookForm form) {
        BookTrace trace = traceDao.get(form.getTraceId());
        if (trace == null) {
            throw new ResourceNotFoundException("book trace not found.");
        }
        if (!trace.getIsbn().equals(form.getIsbn())) {
            throw new InputMismatchException("isbn not match.");
        }
        if (!BookTrace.Status.BORROWED.equals(trace.getStatus())) {
            throw new BusinessException(ErrorCode.BOOK_TRACE_STATUS_MISMATCH);
        }
        Reservation where = new Reservation();
        where.setId(trace.getId());
        where.setStatus(Reservation.Status.WAITING);
        Reservation existReservation = reservationDao.get(where);
        if (existReservation != null) {
            throw new BusinessException(ErrorCode.RESERVATION_EXISTS);
        }
        Reservation reservation = form.toDTO();
        reservation.setUserId(account.getId());
        reservation.setStatus(Reservation.Status.WAITING);
        reservation.setApplyingTime(Date.from(Instant.now()));
        if (0 == reservationDao.add(reservation)) {
            throw new PersistenceException("reservation insert failed.");
        }

        if (0 == recordDao.add(Record.reserve(reservation))) {
            throw new PersistenceException("record failed.");
        }

        return buildReservationVM(reservation);
    }

    public List<ReservationVM> getReservations(@NonNull AccountContext account,
                                               @Valid ReservationListForm form,
                                               @Valid PagingForm paging) {
        Reservation where = new Reservation();
        where.setUserId(account.getId());
        where.setStatus(form.getStatus());
        List<Reservation> reservations = reservationDao.gets(where, paging.getPage(), paging.getLimits());
        return reservations.stream()
                .filter(e -> e != null)
                .distinct()
                .map(this::buildReservationVM)
                .collect(Collectors.toList());
    }

    public ReservationVM getReservation(@NonNull AccountContext account, @NonNull Integer reservationId) {
        Reservation reservation = reservationDao.get(reservationId);
        if (reservation == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        if (!reservation.getUserId().equals(account.getId())) {
            throw new AccessDeniedException("access denied.");
        }
        return buildReservationVM(reservation);
    }

    public void cancelReservation(@NonNull AccountContext account,
                                  @NonNull Integer reservationId) {
        Reservation reservation = reservationDao.get(reservationId);
        if (reservation == null) {
            throw new ResourceNotFoundException("reservation not found.");
        }
        if (!reservation.getUserId().equals(account.getId())) {
            throw new AccessDeniedException("access denied.");
        }
        if (!Reservation.Status.WAITING.equals(reservation.getStatus())) {
            throw new BusinessException(ErrorCode.RESERVATION_STATUS_MISMATCH);
        }
        Reservation where = new Reservation();
        where.setId(reservation.getId());
        where.setStatus(Reservation.Status.WAITING);
        Reservation set = new Reservation();
        set.setStatus(Reservation.Status.CANCELED);
        if (0 == reservationDao.update(where, set)) {
            throw new PersistenceException("reservation update failed.");
        }

        if (0 == recordDao.add(Record.cancelReservation(reservation))) {
            throw new PersistenceException("record failed.");
        }
    }

}
