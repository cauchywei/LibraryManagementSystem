package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import xp.librarian.model.dto.Book;

/**
 * @author xp
 */
@Data
public class BookAddForm implements Serializable {

    private static final long serialVersionUID = 4634452314301768988L;

    @NotNull
    @Length(min = 1, max = 128)
    @Pattern(regexp = "[0-9\\-]+")
    private String isbn;

    @NotNull
    @Length(min = 1, max = 256)
    private String name;

    private Book.Status status;

    @Length(max = 65535)
    private String desc;

    public Book toDTO() {
        return new Book()
                .setIsbn(isbn)
                .setName(name)
                .setStatus(status)
                .setDesc(desc);
    }

}
