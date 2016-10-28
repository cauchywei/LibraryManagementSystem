package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xp.librarian.model.dto.Reservation;

/**
 * @author xp
 */
@Data
public class ReserveBookForm implements Serializable {

    private static final long serialVersionUID = 8475848013383572700L;

    @ApiModelProperty(hidden = true)
    @NotNull
    private String isbn;

    @ApiModelProperty(hidden = true)
    @NotNull
    private Integer traceId;

    public Reservation toDTO() {
        Reservation reservation = new Reservation();
        reservation.setTraceId(traceId);
        return reservation;
    }

    public static ReserveBookForm build(String isbn, Integer traceId) {
        ReserveBookForm form = new ReserveBookForm();
        form.isbn = isbn;
        form.traceId = traceId;
        return form;
    }

}
