package xp.librarian.model.form;

import java.io.*;

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
    private String isbn;

    private BookTrace.Status status;

    private String location;

    public BookTrace toDTO() {
        return new BookTrace()
                .setIsbn(isbn)
                .setStatus(status)
                .setLocation(location);
    }

}
