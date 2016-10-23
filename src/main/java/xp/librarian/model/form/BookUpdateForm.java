package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;

import lombok.Data;
import xp.librarian.model.dto.Book;

/**
 * @author xp
 */
@Data
public class BookUpdateForm implements Serializable {

    private static final long serialVersionUID = 3424097931640604030L;

    @NotNull
    private String isbn;

    private String name;

    private Book.Status status;

    public Book toDTO() {
        Book book = new Book();
        book.setName(name);
        book.setStatus(status);
        return book;
    }

}
