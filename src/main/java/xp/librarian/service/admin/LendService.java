package xp.librarian.service.admin;

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
import xp.librarian.model.form.AdminLendListForm;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.form.UserUpdateForm;
import xp.librarian.model.result.LendVM;
import xp.librarian.repository.*;

/**
 * @author xp
 */
@Service("adminLendService")
@Transactional
public class LendService {

    @Autowired
    private Validator validator;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookTraceDao traceDao;

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

    public List<LendVM> getLends(@Valid AdminLendListForm form,
                                 @Valid PagingForm paging) {
        Set<ConstraintViolation<AdminLendListForm>> vSet = validator.validate(form);
        if (!vSet.isEmpty()) {
            throw new ValidationException(vSet);
        }
        Lend where = new Lend()
                .setUserId(form.getUserId())
                .setStatus(form.getStatus());
        List<Lend> lends = lendDao.gets(where, paging.getPage(), paging.getLimits());
        return lends.stream()
                .filter(e -> e != null)
                .distinct()
                .map(this::buildLendVM)
                .collect(Collectors.toList());
    }

    public LendVM getLend(@NonNull Integer lendId) {
        Lend lend = lendDao.get(lendId);
        if (lend == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        return buildLendVM(lend);
    }

    public void acceptLend(@NonNull Integer lendId) {
        Lend lend = lendDao.get(lendId);
        if (lend == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        if (!Lend.Status.APPLYING.equals(lend.getStatus())) {
            throw new BusinessException(ErrorCode.ADMIN_LEND_STATUS_MISMATCH);
        }
        BookTrace trace = traceDao.get(lend.getTraceId());
        if (trace == null) {
            throw new ResourceNotFoundException("trace not found.");
        }
        if (!BookTrace.Status.LOCKED.equals(trace.getStatus())) {
            throw new BusinessException(ErrorCode.ADMIN_BOOK_TRACE_STATUS_MISMATCH);
        }
        if (!lend.getId().equals(trace.getLendId())) {
            throw new InternalServerException("book trace -> lend not matched.");
        }
        Lend where = new Lend()
                .setId(lendId)
                .setStatus(Lend.Status.APPLYING);
        Lend set = new Lend()
                .setStatus(Lend.Status.ACTIVE);
        if (0 == lendDao.update(where, set)) {
            throw new PersistenceException("lend update failed.");
        }

        BookTrace where1 = new BookTrace()
                .setId(trace.getId())
                .setStatus(BookTrace.Status.LOCKED);
        BookTrace set1 = new BookTrace()
                .setStatus(BookTrace.Status.BORROWED);
        if (0 == traceDao.update(where1, set1)) {
            throw new PersistenceException("book trace update failed.");
        }

        if (0 == recordDao.add(Record.accept(lend))) {
            throw new PersistenceException("record failed.");
        }
    }

    public void rejectLend(@NonNull Integer lendId) {
        Lend lend = lendDao.get(lendId);
        if (lend == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        if (!Lend.Status.APPLYING.equals(lend.getStatus())) {
            throw new BusinessException(ErrorCode.ADMIN_LEND_STATUS_MISMATCH);
        }
        BookTrace trace = traceDao.get(lend.getTraceId());
        if (trace == null) {
            throw new ResourceNotFoundException("trace not found.");
        }
        if (!BookTrace.Status.LOCKED.equals(trace.getStatus())) {
            throw new BusinessException(ErrorCode.ADMIN_BOOK_TRACE_STATUS_MISMATCH);
        }
        if (!lend.getId().equals(trace.getLendId())) {
            throw new InternalServerException("book trace -> lend not matched.");
        }
        Lend where = new Lend()
                .setId(lendId)
                .setStatus(Lend.Status.APPLYING);
        Lend set = new Lend()
                .setStatus(Lend.Status.REJECTED);
        if (0 == lendDao.update(where, set)) {
            throw new PersistenceException("lend update failed.");
        }

        BookTrace where1 = new BookTrace()
                .setId(trace.getId())
                .setStatus(BookTrace.Status.LOCKED);
        BookTrace set1 = new BookTrace()
                .setStatus(BookTrace.Status.NORMAL)
                .setLendId(0);
        if (0 == traceDao.update(where1, set1)) {
            throw new PersistenceException("book trace update failed.");
        }

        if (0 == recordDao.add(Record.reject(lend))) {
            throw new PersistenceException("record failed.");
        }
    }

    public void confirmLendReturned(@NonNull Integer lendId) {
        Lend lend = lendDao.get(lendId);
        if (lend == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        if (!Lend.Status.ACTIVE.equals(lend.getStatus())) {
            throw new BusinessException(ErrorCode.ADMIN_LEND_STATUS_MISMATCH);
        }
        BookTrace trace = traceDao.get(lend.getTraceId());
        if (trace == null) {
            throw new ResourceNotFoundException("trace not found.");
        }
        if (!BookTrace.Status.BORROWED.equals(trace.getStatus())) {
            throw new BusinessException(ErrorCode.ADMIN_BOOK_TRACE_STATUS_MISMATCH);
        }
        if (!lend.getId().equals(trace.getLendId())) {
            throw new InternalServerException("book trace -> lend not matched.");
        }
        Lend whereA = new Lend()
                .setId(lendId)
                .setStatus(Lend.Status.ACTIVE);
        Lend whereB = new Lend()
                .setId(lendId)
                .setStatus(Lend.Status.LATE);
        Lend set = new Lend()
                .setStatus(Lend.Status.RETURNED);
        if (0 == lendDao.update(whereA, set) && 0 == lendDao.update(whereB, set)) {
            throw new PersistenceException("lend update failed.");
        }

        BookTrace where1 = new BookTrace()
                .setId(trace.getId())
                .setStatus(BookTrace.Status.BORROWED);
        BookTrace set1 = new BookTrace()
                .setStatus(BookTrace.Status.LOCKED)
                .setLendId(0);
        if (0 == traceDao.update(where1, set1)) {
            throw new PersistenceException("book trace update failed.");
        }

        if (0 == recordDao.add(Record.confirmReturned(lend))) {
            throw new PersistenceException("record failed.");
        }
    }

    public void confirmLendDisabled(@NonNull Integer lendId) {
        Lend lend = lendDao.get(lendId);
        if (lend == null) {
            throw new ResourceNotFoundException("lend not found.");
        }
        if (!Lend.Status.ACTIVE.equals(lend.getStatus())) {
            throw new BusinessException(ErrorCode.ADMIN_LEND_STATUS_MISMATCH);
        }
        BookTrace trace = traceDao.get(lend.getTraceId());
        if (trace == null) {
            throw new ResourceNotFoundException("trace not found.");
        }
        if (!BookTrace.Status.BORROWED.equals(trace.getStatus())) {
            throw new BusinessException(ErrorCode.ADMIN_BOOK_TRACE_STATUS_MISMATCH);
        }
        if (!lend.getId().equals(trace.getLendId())) {
            throw new InternalServerException("book trace -> lend not matched.");
        }
        Lend whereA = new Lend()
                .setId(lendId)
                .setStatus(Lend.Status.ACTIVE);
        Lend whereB = new Lend()
                .setId(lendId)
                .setStatus(Lend.Status.LATE);
        Lend set = new Lend()
                .setStatus(Lend.Status.DISABLED);
        if (0 == lendDao.update(whereA, set) && 0 == lendDao.update(whereB, set)) {
            throw new PersistenceException("lend update failed.");
        }

        BookTrace where1 = new BookTrace()
                .setId(trace.getId())
                .setStatus(BookTrace.Status.BORROWED);
        BookTrace set1 = new BookTrace()
                .setStatus(BookTrace.Status.DELETED);
        if (0 == traceDao.update(where1, set1)) {
            throw new PersistenceException("book trace update failed.");
        }

        if (0 == recordDao.add(Record.confirmDisabled(lend))) {
            throw new PersistenceException("record failed.");
        }
    }

}
