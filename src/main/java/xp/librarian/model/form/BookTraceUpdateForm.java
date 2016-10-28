package xp.librarian.model.form;

import java.io.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xp.librarian.model.dto.BookTrace;

/**
 * @author xp
 */
@Data
public class BookTraceUpdateForm implements Serializable {

    private static final long serialVersionUID = 5069386085980515997L;

    @ApiModelProperty(hidden = true)
    private String isbn;

    @ApiModelProperty(hidden = true)
    private Integer traceId;

    private BookTrace.Status status;

    private String location;

    public BookTrace toDTO() {
        BookTrace trace = new BookTrace();
        trace.setStatus(status);
        trace.setLocation(location);
        return trace;
    }

}
