package xp.librarian.model.form;

import java.io.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xp.librarian.model.dto.BookTrace;

/**
 * @author xp
 */
@Data
public class BookTraceAddForm implements Serializable {

    private static final long serialVersionUID = -4892039389283782045L;

    @ApiModelProperty(hidden = true)
    @NotBlank
    private String isbn;

    private BookTrace.Status status;

    @Length(max = 63325)
    private String location;

    public BookTrace toDTO() {
        return new BookTrace()
                .setIsbn(isbn)
                .setStatus(status)
                .setLocation(location);
    }

}
