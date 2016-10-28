package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xp.librarian.model.dto.Book;

/**
 * @author xp
 */
@Data
public class BookUpdateForm implements Serializable {

    private static final long serialVersionUID = 3424097931640604030L;

    @ApiModelProperty(hidden = true)
    @NotNull
    private String isbn;

    private String name;

    private Book.Status status;

    private String desc;

    public Book toDTO() {
        return new Book()
                .setName(name)
                .setStatus(status)
                .setDesc(desc);
    }

}
