package xp.librarian.model.form;

import java.io.*;

import lombok.Data;
import xp.librarian.model.dto.Lend;

/**
 * @author xp
 */
@Data
public class AdminLendListForm implements Serializable {

    private static final long serialVersionUID = -438526386140204211L;

    private Integer userId;

    private Lend.Status status;

}
