package xp.librarian.service.reader;

import java.util.*;
import java.util.stream.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import xp.librarian.model.context.*;
import xp.librarian.model.dto.*;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.form.ReservationListForm;
import xp.librarian.model.form.ReserveBookForm;
import xp.librarian.model.form.UserUpdateForm;
import xp.librarian.model.result.ReservationVM;
import xp.librarian.repository.*;
import xp.librarian.utils.TimeUtils;

/**
 * @author xp
 */
@Service("readerReservationService")
@Transactional
public class ReservationService {

    @Autowired
    private Validator validator;

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
        Set<ConstraintViolation<ReserveBookForm>> vSet = validator.validate(form);
        if (!vSet.isEmpty()) {
            throw new ValidationException(vSet);
        }
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
        Reservation where = new Reservation()
                .setTraceId(trace.getId())
                .setStatus(Reservation.Status.WAITING);
        Reservation existReservation = reservationDao.get(where);
        if (existReservation != null) {
            throw new BusinessException(ErrorCode.RESERVATION_EXISTS);
        }
        Reservation reservation = form.toDTO()
                .setUserId(account.getId())
                .setStatus(Reservation.Status.WAITING)
                .setApplyingTime(TimeUtils.now());
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
        Set<ConstraintViolation<ReservationListForm>> vSet = validator.validate(form);
        if (!vSet.isEmpty()) {
            throw new ValidationException(vSet);
        }
        Reservation where = new Reservation()
                .setUserId(account.getId())
                .setStatus(form.getStatus());
        List<Reservation> reservations = reservationDao.gets(where, paging.getPage(), paging.getLimits());
        return reservations.stream()
                .filter(e -> e != null)
                .distinct()
                .map(this::buildReservationVM)
                .collect(Collectors.toList());
    }

    public ReservationVM getReservation(@NonNull AccountContext account,
                                        @NonNull Integer reservationId) {
        Reservation reservation = reservationDao.get(reservationId);
        if (reservation == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        if (!reservation.getUserId().equals(account.getId())) {
            throw new AccessForbiddenException("access denied.");
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
            throw new AccessForbiddenException("access denied.");
        }
        if (!Reservation.Status.WAITING.equals(reservation.getStatus())) {
            throw new BusinessException(ErrorCode.RESERVATION_STATUS_MISMATCH);
        }
        Reservation where = new Reservation()
                .setId(reservation.getId())
                .setStatus(Reservation.Status.WAITING);
        Reservation set = new Reservation()
                .setStatus(Reservation.Status.CANCELED);
        if (0 == reservationDao.update(where, set)) {
            throw new PersistenceException("reservation update failed.");
        }

        if (0 == recordDao.add(Record.cancelReservation(reservation))) {
            throw new PersistenceException("record failed.");
        }
    }

}
