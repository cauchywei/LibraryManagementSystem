package xp.librarian.service.reader;

import java.time.temporal.*;
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
import xp.librarian.model.form.LendBookForm;
import xp.librarian.model.form.LendListForm;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.LendVM;
import xp.librarian.repository.*;
import xp.librarian.utils.TimeUtils;

/**
 * @author xp
 */
@Service("readerLendService")
@Transactional
public class LendService {

    @Autowired
    private Validator validator;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookTraceDao traceDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private LendDao lendDao;

    @Autowired
    private RecordDao recordDao;

    private LendVM buildLendVM(@NonNull Lend lend) {
        User user = userDao.get(lend.getUserId(), true);
        if (user == null) {
            throw new ResourceNotFoundException("user not found.");
        }
        BookTrace trace = traceDao.get(lend.getTraceId(), true);
        if (trace == null) {
            throw new ResourceNotFoundException("book trace not found.");
        }
        Book book = bookDao.get(trace.getIsbn(), true);
        if (book == null) {
            throw new ResourceNotFoundException("book not found.");
        }
        return new LendVM().withLend(lend).withUser(user).withTrace(trace, book);
    }

    public LendVM lendBook(@NonNull AccountContext account,
                           @Valid LendBookForm form) {
        Set<ConstraintViolation<LendBookForm>> vSet = validator.validate(form);
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
        BookTrace where = new BookTrace()
                .setId(trace.getId())
                .setStatus(BookTrace.Status.NORMAL);
        BookTrace set = new BookTrace()
                .setStatus(BookTrace.Status.LOCKED);
        if (0 == traceDao.update(where, set)) {
            throw new BusinessException(ErrorCode.BOOK_TRACE_HAS_BEEN_LOCKED);
        }

        Lend lend = form.toDTO()
                .setUserId(account.getId())
                .setStatus(Lend.Status.APPLYING)
                .setApplyingTime(TimeUtils.now())
                .setExpiredTime(TimeUtils.afterNow(2L, ChronoUnit.HOURS));
        if (0 == lendDao.add(lend)) {
            throw new PersistenceException("lend insert failed.");
        }

        where = new BookTrace()
                .setId(trace.getId())
                .setStatus(BookTrace.Status.LOCKED);
        set = new BookTrace()
                .setLendId(lend.getId());
        if (0 == traceDao.update(where, set)) {
            throw new PersistenceException("book trace update failed.");
        }

        if (0 == recordDao.add(Record.apply(lend))) {
            throw new PersistenceException("record failed.");
        }

        return buildLendVM(lend);
    }

    public List<LendVM> getLends(@NonNull AccountContext account,
                                 @Valid LendListForm form,
                                 @Valid PagingForm paging) {
        Set<ConstraintViolation<LendListForm>> vSet = validator.validate(form);
        if (!vSet.isEmpty()) {
            throw new ValidationException(vSet);
        }
        Lend where = new Lend()
                .setUserId(account.getId())
                .setStatus(form.getStatus());
        List<Lend> lends = lendDao.gets(where, paging.getPage(), paging.getLimits());
        return lends.stream()
                .filter(e -> e != null)
                .distinct()
                .map(this::buildLendVM)
                .collect(Collectors.toList());
    }

    public LendVM getLend(@NonNull AccountContext account,
                          @NonNull Integer lendId) {
        Lend lend = lendDao.get(lendId);
        if (lend == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        if (!lend.getUserId().equals(account.getId())) {
            throw new AccessForbiddenException("access denied.");
        }
        return buildLendVM(lend);
    }

    public void cancelLend(@NonNull AccountContext account,
                           @NonNull Integer lendId) {
        Lend lend = lendDao.get(lendId);
        if (lend == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        if (!lend.getUserId().equals(account.getId())) {
            throw new AccessForbiddenException("access denied.");
        }
        Lend where = new Lend()
                .setId(lend.getId())
                .setStatus(Lend.Status.APPLYING);
        Lend set = new Lend()
                .setStatus(Lend.Status.CANCELED);
        if (0 == lendDao.update(where, set)) {
            throw new PersistenceException("lend update failed.");
        }

        if (0 == recordDao.add(Record.cancelApplication(lend))) {
            throw new PersistenceException("record failed.");
        }
    }

    public LendVM renewLend(@NonNull AccountContext account,
                            @NonNull Integer lendId) {
        Lend lend = lendDao.get(lendId);
        if (lend == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        if (!lend.getUserId().equals(account.getId())) {
            throw new AccessForbiddenException("access denied.");
        }
        if (!Lend.Status.ACTIVE.equals(lend.getStatus())) {
            throw new BusinessException(ErrorCode.BOOK_TRACE_HAS_BEEN_LOCKED);
        }
        if (lend.getRenew() != 0) {
            throw new BusinessException(ErrorCode.LEND_RENEW_ONLY_ONCE_ALLOWED);
        }
        Lend where = new Lend()
                .setId(lend.getId())
                .setStatus(Lend.Status.ACTIVE)
                .setRenew(lend.getRenew())
                .setAppointedTime(lend.getAppointedTime());
        Lend set = new Lend()
                .setRenew(lend.getRenew() + 1)
                .setAppointedTime(TimeUtils.afterNow(30L, ChronoUnit.DAYS));
        if (0 == lendDao.update(where, set)) {
            throw new PersistenceException("lend update failed.");
        }

        if (0 == recordDao.add(Record.renewLend(lend))) {
            throw new PersistenceException("record failed.");
        }
        return buildLendVM(lendDao.get(lendId));
    }

}
