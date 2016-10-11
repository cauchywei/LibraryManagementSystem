package xp.librarian.model.result;

import java.io.*;
import java.util.*;

import lombok.Data;
import xp.librarian.model.dto.BookDto;
import xp.librarian.model.dto.BorrowRecordDto;

/**
 * @author xp
 */
@Data
public class BorrowRecordVM implements Serializable {

    private static final long serialVersionUID = 4229075844008454538L;

    private Integer id;

    private Integer userId;

    private String isbn;

    private BorrowRecordDto.Status status;

    private Date borrowTime;

    private Date returnTime;

    private BookVM book;

    public BorrowRecordVM withBorrowRecord(BorrowRecordDto borrowRecord) {
        if (borrowRecord != null) {
            this.id = borrowRecord.getId();
            this.userId = borrowRecord.getUserId();
            this.isbn = borrowRecord.getIsbn();
            this.status = borrowRecord.getStatus();
            this.borrowTime = borrowRecord.getBorrowTime();
            this.returnTime = borrowRecord.getReturnTime();
        }
        return this;
    }

    public BorrowRecordVM withBook(BookDto book) {
        this.book = new BookVM().withBook(book);
        return this;
    }

}
