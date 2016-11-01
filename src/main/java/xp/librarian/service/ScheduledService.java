package xp.librarian.service;

import java.time.*;
import java.time.temporal.*;
import java.util.*;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xp.librarian.model.dto.BookTrace;
import xp.librarian.model.dto.Lend;
import xp.librarian.model.dto.Record;
import xp.librarian.model.dto.Reservation;
import xp.librarian.repository.*;
import xp.librarian.utils.TimeUtils;

/**
 * @author xp
 */
@Service
@Transactional
public class ScheduledService {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookTraceDao traceDao;

    @Autowired
    private LendDao lendDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private RecordDao recordDao;

    @Scheduled(fixedDelay = 5 * 1000)
    public void lendExpiryTick() {
        Instant now = TimeUtils.now();
        Lend where = new Lend()
                .setStatus(Lend.Status.APPLYING);
        List<Lend> lends = lendDao.gets(where, 1, 0);
        lends.stream()
                .filter(lend -> now.isAfter(lend.getExpiredTime()))
                .forEach(lend -> {
                    Lend where1 = new Lend()
                            .setId(lend.getId())
                            .setStatus(Lend.Status.APPLYING);
                    Lend set1 = new Lend()
                            .setStatus(Lend.Status.EXPIRED);
                    if (0 == lendDao.update(where1, set1)) {
                        LOG.info(String.format("lend (%d) update failed.", lend.getId()));
                        return;
                    }
                    BookTrace trace = traceDao.get(lend.getTraceId());
                    if (trace != null) {
                        BookTrace where2 = new BookTrace()
                                .setId(trace.getId())
                                .setStatus(BookTrace.Status.LOCKED);
                        BookTrace set2 = new BookTrace()
                                .setStatus(BookTrace.Status.NORMAL)
                                .setLendId(0);
                        if (0 == traceDao.update(where2, set2)) {
                            LOG.info(String.format("book trace (%d) update failed.", trace.getId()));
                            return;
                        }
                    }

                    if (0 == recordDao.add(Record.expired(lend))) {
                        throw new PersistenceException("record failed.");
                    }
                });
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void lendAppointmentTick() {
        Instant now = TimeUtils.now();
        Lend where = new Lend()
                .setStatus(Lend.Status.ACTIVE);
        List<Lend> lends = lendDao.gets(where, 1, 0);
        lends.stream()
                .filter(lend -> now.isAfter(lend.getAppointedTime()))
                .forEach(lend -> {
                    Lend where1 = new Lend()
                            .setId(lend.getId())
                            .setStatus(Lend.Status.ACTIVE);
                    Lend set1 = new Lend()
                            .setStatus(Lend.Status.LATE);
                    if (0 == lendDao.update(where1, set1)) {
                        LOG.info(String.format("lend (%d) update failed.", lend.getId()));
                        return;
                    }

                    if (0 == recordDao.add(Record.expired(lend))) {
                        throw new PersistenceException("record failed.");
                    }
                });
    }

    @Scheduled(fixedDelay = 5 * 1000)
    public void reservationTick() {
        BookTrace where = new BookTrace()
                .setStatus(BookTrace.Status.LOCKED)
                .setLendId(0);
        List<BookTrace> traces = traceDao.gets(where, 1, 0);

        traces.forEach(trace -> {
            BookTrace where1 = new BookTrace()
                    .setId(trace.getId())
                    .setStatus(BookTrace.Status.LOCKED)
                    .setLendId(0);

            Reservation where2 = new Reservation()
                    .setTraceId(trace.getId())
                    .setStatus(Reservation.Status.WAITING);
            Reservation reservation = reservationDao.get(where2);
            if (reservation == null) {
                // 无人预订
                BookTrace set1 = new BookTrace()
                        .setStatus(BookTrace.Status.NORMAL);
                if (0 == traceDao.update(where1, set1)) {
                    throw new PersistenceException("book trace update failed.");
                }
                return;
            }
            Reservation set2 = new Reservation()
                    .setStatus(Reservation.Status.ENABLED)
                    .setEnabledTime(TimeUtils.now());
            if (0 == reservationDao.update(where2, set2)) {
                throw new PersistenceException("reservation update failed.");
            }

            Lend lend = new Lend()
                    .setTraceId(reservation.getTraceId())
                    .setUserId(reservation.getUserId())
                    .setStatus(Lend.Status.APPLYING)
                    .setApplyingTime(TimeUtils.now())
                    .setAppointedTime(TimeUtils.afterNow(30L, ChronoUnit.DAYS))
                    .setExpiredTime(TimeUtils.afterNow(24L, ChronoUnit.HOURS));
            if (0 == lendDao.add(lend)) {
                throw new PersistenceException("lend insert failed.");
            }

            BookTrace set1 = new BookTrace()
                    .setLendId(lend.getId());
            if (0 == traceDao.update(where1, set1)) {
                throw new PersistenceException("book trace update failed.");
            }

            if (0 == recordDao.add(Record.reserved(reservation))) {
                throw new PersistenceException("record failed.");
            }
        });
    }

}
