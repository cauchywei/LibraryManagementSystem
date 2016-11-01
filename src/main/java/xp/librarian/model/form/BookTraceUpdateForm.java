package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

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
    @NotBlank
    private String isbn;

    @ApiModelProperty(hidden = true)
    @NotNull
    private Integer traceId;

    private BookTrace.Status status;

    @Length(max = 63325)
    private String location;

    public BookTrace toDTO() {
        return new BookTrace()
                .setStatus(status)
                .setLocation(location);
    }

}
