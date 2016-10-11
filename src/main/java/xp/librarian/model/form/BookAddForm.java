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
    private String isbn;

    private String name;

    private Integer total;

    private Integer margin;

    public BookDto toDTO() {
        BookDto book = new BookDto();
        book.setIsbn(isbn);
        book.setName(name);
        book.setTotal(total);
        book.setMargin(margin);
        return book;
    }

}
