package xp.librarian.model.form;

import java.io.*;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * @author xp
 */
@Data
public class BookSearchForm implements Serializable {

    private static final long serialVersionUID = -9155511439197299065L;

    @Length(max = 128)
    private String isbn;

    @Length(max = 256)
    private String name;

}
