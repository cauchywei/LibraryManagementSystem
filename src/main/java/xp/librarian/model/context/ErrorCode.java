package xp.librarian.model.context;

import lombok.Getter;

/**
 * @author xp
 */
public enum ErrorCode {
    USER_EXISTS("user.exists"),
    USER_LOGIN_FAIL("user.login_fail"),
    BOOK_TRACE_HAS_BEEN_LOCKED("book_trace.locked"),
    BOOK_TRACE_STATUS_MISMATCH("book_trace.status_mismatch"),
    LEND_STATUS_MISMATCH("lend.status_mismatch"),
    RESERVATION_STATUS_MISMATCH("reservation.status_mismatch"),
    RESERVATION_EXISTS("reservation.exists"),

    ADMIN_BOOK_STATUS_MISMATCH("admin.book.status_mismatch"),
    ADMIN_BOOK_TRACE_STATUS_MISMATCH("admin.book_trace.status_mismatch"),
    ADMIN_LEND_STATUS_MISMATCH("admin.lend.status_mismatch"),
    ADMIN_RESERVATION_STATUS_MISMATCH("admin.reservation.status_mismatch"),
    ;

    @Getter
    private String key;

    ErrorCode(String key) {
        this.key = key;
    }
}
