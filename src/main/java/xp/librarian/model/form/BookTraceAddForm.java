package xp.librarian.model.form;

import java.io.*;

import lombok.Data;
import xp.librarian.model.dto.BookTrace;

/**
 * @author xp
 */
@Data
public class BookTraceAddForm implements Serializable {

    private static final long serialVersionUID = -4892039389283782045L;

    private String isbn;

    private BookTrace.Status status;

    private String location;

    public BookTrace toDTO() {
        BookTrace trace = new BookTrace();
        trace.setIsbn(isbn);
        trace.setStatus(status);
        trace.setLocation(location);
        return trace;
    }

}
