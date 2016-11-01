package xp.librarian.model.form;

import java.io.*;
import java.time.*;
import java.time.temporal.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xp.librarian.model.dto.Lend;

/**
 * @author xp
 */
@Data
public class LendBookForm implements Serializable {

    private static final long serialVersionUID = -2544070182439216592L;

    @ApiModelProperty(hidden = true)
    @NotBlank
    private String isbn;

    @ApiModelProperty(hidden = true)
    @NotNull
    private Integer traceId;

    @Future
    private Long appointedTime;

    public Lend toDTO() {
        Lend lend = new Lend();
        lend.setTraceId(traceId);
        if (appointedTime != null) {
            lend.setAppointedTime(Instant.ofEpochMilli(appointedTime));
        } else {
            lend.setAppointedTime(Instant.now().plus(30L, ChronoUnit.DAYS));
        }
        return lend;
    }

}
