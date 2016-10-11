package xp.librarian.model.form;

import java.io.*;

import lombok.Data;

/**
 * @author xp
 */
@Data
public class PagingForm implements Serializable {

    private static final long serialVersionUID = 6992199899328251070L;

    private Integer page = 1;

    private Integer limits = 20;

}
