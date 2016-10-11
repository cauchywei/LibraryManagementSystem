package xp.librarian.model.result;

import java.io.*;
import java.util.*;

import lombok.Data;
import xp.librarian.model.dto.BookDto;

/**
 * @author xp
 */
@Data
public class BookVM implements Serializable {

    private static final long serialVersionUID = 5131299075378859816L;

    private String isbn;

    private String name;

    private Integer total;

    private Integer margin;

    private Date createTime;

    public BookVM withBook(BookDto book) {
        if (book != null) {
            this.isbn = book.getIsbn();
            this.name = book.getName();
            this.total = book.getTotal();
            this.margin = book.getMargin();
            this.createTime = book.getCreateTime();
        }
        return this;
    }

}
