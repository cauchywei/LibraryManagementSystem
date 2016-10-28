package xp.librarian.model.dto;

import java.io.*;
import java.util.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xp
 */
@Getter
@Setter
public class Book implements Serializable {

    private static final long serialVersionUID = -2528891458266093773L;

    public String isbn;

    public String name;

    public enum Status {
        NORMAL,
        DELETED,
        ;
    }

    public Status status;

    public transient Integer total;

    public transient Integer margin;

    public Date createTime;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Book)) return false;
        Book book = (Book) object;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
