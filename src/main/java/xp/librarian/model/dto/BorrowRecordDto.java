package xp.librarian.model.dto;

import java.io.*;
import java.util.*;

import lombok.Data;

/**
 * @author xp
 */
@Data
public class BorrowRecordDto implements Serializable {

    private static final long serialVersionUID = 7741202507373698255L;

    public enum Status {
        BORROWING("BORROWING"),
        RETURNED("RETURNED");

        private String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private Integer id;

    private Integer userId;

    private String isbn;

    private Status status;

    private Date borrowTime;

    private Date returnTime;

}
