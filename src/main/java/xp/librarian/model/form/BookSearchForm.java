package xp.librarian.model.form;

import java.io.*;

import lombok.Data;

/**
 * @author xp
 */
@Data
public class BookSearchForm implements Serializable {

    private static final long serialVersionUID = -9155511439197299065L;

    private String ISBN;

    private String name;

}
