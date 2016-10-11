package xp.librarian.service;

import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xp.librarian.model.context.AccountContext;
import xp.librarian.model.dto.BookDto;
import xp.librarian.model.dto.BorrowRecordDto;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.BorrowRecordVM;
import xp.librarian.repository.BookDao;
import xp.librarian.repository.BorrowRecordDao;
import xp.librarian.utils.BusinessException;

/**
 * @author xp
 */
@Service
@Transactional
public class BorrowService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BorrowRecordDao borrowRecordDao;

    public boolean borrow(AccountContext account, String ISBN) {
        if (1 != bookDao.updateMargin(ISBN, -1)) {
            return false;
        }
        BorrowRecordDto record = new BorrowRecordDto();
        record.setUserId(account.getId());
        record.setIsbn(ISBN);
        record.setStatus(BorrowRecordDto.Status.BORROWING);
        record.setBorrowTime(new Date(System.currentTimeMillis()));
        return 1 == borrowRecordDao.add(record);
    }

    public boolean revert(AccountContext account, String ISBN) {
        BorrowRecordDto record = borrowRecordDao.get(account.getId(), ISBN, BorrowRecordDto.Status.BORROWING);
        if (record == null) {
            throw new BusinessException("no borrow, no return.");
        }
        if (1 != bookDao.updateMargin(ISBN, 1)) {
            return false;
        }
        record.setStatus(BorrowRecordDto.Status.RETURNED);
        record.setReturnTime(new Date(System.currentTimeMillis()));
        return 1 == borrowRecordDao.update(record);
    }

    public List<BorrowRecordVM> getRecords(AccountContext account, PagingForm paging) {
        List<BorrowRecordDto> records = borrowRecordDao.gets(account.getId(), paging.getPage(), paging.getLimits());
        Set<String> ISBNs = records.stream().map(BorrowRecordDto::getIsbn).collect(Collectors.toSet());
        Map<String, BookDto> books = bookDao.gets(ISBNs);
        return records.stream()
                .filter((e) -> e != null && books.containsKey(e.getIsbn()))
                .map((e) -> new BorrowRecordVM().withBorrowRecord(e).withBook(books.get(e.getIsbn())))
                .collect(Collectors.toList());
    }

}
