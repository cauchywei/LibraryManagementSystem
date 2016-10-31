package xp.librarian.model.result;

import java.io.*;
import java.time.*;
import java.util.*;

import lombok.Data;
import xp.librarian.model.dto.Book;

/**
 * @author xp
 */
@Data
public class BookVM implements Serializable {

    private static final long serialVersionUID = 5131299075378859816L;

    private String isbn;

    private String name;

    private String desc;

    private Integer total;

    private Integer margin;

    private Long createTime;

    public BookVM withBook(Book book) {
        if (book != null) {
            this.isbn = book.getIsbn();
            this.name = book.getName();
            this.desc = book.getDesc();
            this.total = book.getTotal();
            this.margin = book.getMargin();
            this.createTime = Optional.ofNullable(book.getCreateTime()).map(Instant::toEpochMilli).orElse(null);
        }
        return this;
    }

}
