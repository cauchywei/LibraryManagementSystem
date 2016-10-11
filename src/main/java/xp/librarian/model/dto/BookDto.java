package xp.librarian.model.dto;

import java.io.*;
import java.util.*;

import lombok.Data;

/**
 * @author xp
 */
@Data
public class BookDto implements Serializable {

    private static final long serialVersionUID = -2528891458266093773L;

    public enum Status {
        NORMAL("NORMAL"),
        DELETE("DELETE");

        private String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private String ISBN;

    private Status status;

    private String name;

    private Integer total;

    private Integer margin;

    private Date createTime;

}
