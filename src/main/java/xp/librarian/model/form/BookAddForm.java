package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;

import lombok.Data;
import xp.librarian.model.dto.BookDto;

/**
 * @author xp
 */
@Data
public class BookAddForm implements Serializable {

    private static final long serialVersionUID = 4634452314301768988L;

    @NotNull
    private String ISBN;

    private String name;

    private Integer total;

    public BookDto toDTO() {
        BookDto book = new BookDto();
        book.setISBN(ISBN);
        book.setName(name);
        book.setTotal(total);
        return book;
    }

}
