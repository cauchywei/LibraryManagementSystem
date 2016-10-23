package xp.librarian.model.form;

import java.io.*;
import java.util.*;

import javax.validation.constraints.NotNull;

import lombok.Data;
import xp.librarian.model.dto.Lend;

/**
 * @author xp
 */
@Data
public class LendBookForm implements Serializable {

    private static final long serialVersionUID = -2544070182439216592L;

    @NotNull
    private String isbn;

    @NotNull
    private Integer traceId;

    @NotNull
    private Long appointedTime;

    public Lend toDTO() {
        Lend lend = new Lend();
        lend.setTraceId(traceId);
        lend.setAppointedTime(new Date(appointedTime));
        return lend;
    }

    public static LendBookForm build(String isbn, Integer traceId) {
        LendBookForm form = new LendBookForm();
        form.isbn = isbn;
        form.traceId = traceId;
        return form;
    }

}
