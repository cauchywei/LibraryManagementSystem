package xp.librarian.model.form;

import java.io.*;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

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
    @NotBlank
    private String isbn;

    @Length(min = 1, max = 256)
    private String name;

    private Book.Status status;

    @Length(max = 63325)
    private String desc;

    public Book toDTO() {
        return new Book()
                .setName(name)
                .setStatus(status)
                .setDesc(desc);
    }

}
