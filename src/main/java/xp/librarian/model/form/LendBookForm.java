package xp.librarian.model.form;

import java.io.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;
import xp.librarian.model.dto.Lend;

/**
 * @author xp
 */
@Data
public class LendBookForm implements Serializable {

    private static final long serialVersionUID = -2544070182439216592L;

    @ApiModelProperty(hidden = true)
    @NotNull
    private String isbn;

    @ApiModelProperty(hidden = true)
    @NotNull
    private Integer traceId;

    @NotNull
    private Long appointedTime;

    public Lend toDTO() {
        Lend lend = new Lend();
        lend.setTraceId(traceId);
        if (appointedTime != null) {
            lend.setAppointedTime(new Date(appointedTime));
        } else {
            lend.setAppointedTime(Date.from(Instant.now().plus(30L, ChronoUnit.DAYS)));
        }
        return lend;
    }

}
