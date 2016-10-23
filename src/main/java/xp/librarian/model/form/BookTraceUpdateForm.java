package xp.librarian.model.form;

import java.io.*;

import lombok.Data;
import xp.librarian.model.dto.BookTrace;

/**
 * @author xp
 */
@Data
public class BookTraceUpdateForm implements Serializable {

    private static final long serialVersionUID = 5069386085980515997L;

    private Integer traceId;

    private String isbn;

    private BookTrace.Status status;

    private String location;

    public BookTrace toDTO() {
        BookTrace trace = new BookTrace();
        trace.setStatus(status);
        trace.setLocation(location);
        return trace;
    }

}
