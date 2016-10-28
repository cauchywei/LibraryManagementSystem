package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;

import lombok.Data;
import xp.librarian.model.dto.Book;

/**
 * @author xp
 */
@Data
public class BookAddForm implements Serializable {

    private static final long serialVersionUID = 4634452314301768988L;

    @NotNull
    private String isbn;

    private String name;

    private Book.Status status;

    private String desc;

    public Book toDTO() {
        return new Book()
                .setIsbn(isbn)
                .setName(name)
                .setStatus(status)
                .setDesc(desc);
    }

}
