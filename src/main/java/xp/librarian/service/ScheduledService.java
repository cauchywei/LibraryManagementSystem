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
        Lend where = new Lend();
        where.setStatus(Lend.Status.APPLYING);
        List<Lend> lends = lendDao.gets(where, 1, 0);
        lends.stream()
                .filter(lend -> now.isAfter(lend.getExpiredTime()))
                .forEach(lend -> {
                    Lend where1 = new Lend();
                    where1.setId(lend.getId());
                    where1.setStatus(Lend.Status.APPLYING);
                    Lend set1 = new Lend();
                    set1.setStatus(Lend.Status.EXPIRED);
                    if (0 == lendDao.update(where1, set1)) {
                        LOG.info(String.format("lend (%d) update failed.", lend.getId()));
                        return;
                    }
                    BookTrace trace = traceDao.get(lend.getTraceId());
                    if (trace != null) {
                        BookTrace where2 = new BookTrace();
                        where2.setId(trace.getId());
                        where2.setStatus(BookTrace.Status.LOCKED);
                        BookTrace set2 = new BookTrace();
                        set2.setStatus(BookTrace.Status.NORMAL);
                        set2.setLendId(0);
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
        Lend where = new Lend();
        where.setStatus(Lend.Status.ACTIVE);
        List<Lend> lends = lendDao.gets(where, 1, 0);
        lends.stream()
                .filter(lend -> now.isAfter(lend.getAppointedTime()))
                .forEach(lend -> {
                    Lend where1 = new Lend();
                    where1.setId(lend.getId());
                    where1.setStatus(Lend.Status.ACTIVE);
                    Lend set1 = new Lend();
                    set1.setStatus(Lend.Status.LATE);
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
        BookTrace where = new BookTrace();
        where.setStatus(BookTrace.Status.LOCKED);
        where.setLendId(0);
        List<BookTrace> traces = traceDao.gets(where, 1, 0);

        traces.forEach(trace -> {
            BookTrace where1 = new BookTrace();
            where1.setId(trace.getId());
            where1.setStatus(BookTrace.Status.LOCKED);
            where1.setLendId(0);
            BookTrace set1 = new BookTrace();

            Reservation where2 = new Reservation();
            where2.setTraceId(trace.getId());
            where2.setStatus(Reservation.Status.WAITING);
            Reservation reservation = reservationDao.get(where2);
            if (reservation == null) {
                // 无人预订
                set1.setStatus(BookTrace.Status.NORMAL);
                if (0 == traceDao.update(where1, set1)) {
                    throw new PersistenceException("book trace update failed.");
                }
                return;
            }
            Reservation set2 = new Reservation();
            set2.setStatus(Reservation.Status.ENABLED);
            set2.setEnabledTime(TimeUtils.now());
            if (0 == reservationDao.update(where2, set2)) {
                throw new PersistenceException("reservation update failed.");
            }

            Lend lend = new Lend();
            lend.setAppointedTime(TimeUtils.afterNow(30L, ChronoUnit.DAYS));
            lend.setUserId(reservation.getUserId());
            lend.setStatus(Lend.Status.APPLYING);
            lend.setExpiredTime(TimeUtils.afterNow(24L, ChronoUnit.HOURS));
            lend.setApplyingTime(TimeUtils.now());
            if (0 == lendDao.add(lend)) {
                throw new PersistenceException("lend insert failed.");
            }

            set1.setLendId(lend.getId());
            if (0 == traceDao.update(where1, set1)) {
                throw new PersistenceException("book trace update failed.");
            }

            if (0 == recordDao.add(Record.reserved(reservation))) {
                throw new PersistenceException("record failed.");
            }
        });
    }

}
