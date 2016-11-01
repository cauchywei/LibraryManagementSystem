package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

/**
 * @author xp
 */
@Data
public class PagingForm implements Serializable {

    private static final long serialVersionUID = 6992199899328251070L;

    @Min(1L)
    private Integer page = 1;

    @Range(min = 0L, max = 256L)
    private Integer limits = 20;

}
