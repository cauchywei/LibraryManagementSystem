package xp.librarian.model.result;

import java.io.*;
import java.util.*;

import lombok.Data;
import xp.librarian.model.dto.Action;
import xp.librarian.model.dto.Book;
import xp.librarian.model.dto.Lend;
import xp.librarian.model.dto.Record;

/**
 * @author xp
 */
@Data
public class RecordVM implements Serializable {

    private static final long serialVersionUID = 4229075844008454538L;

    private Integer id;

    private Integer userId;

    private Integer traceId;

    private Action action;

    private Long time;

    public RecordVM withRecord(Record record) {
        if (record != null) {
            this.id = record.getId();
            this.userId = record.getUserId();
            this.traceId = record.getTraceId();
            this.action = record.getAction();
            this.time = Optional.ofNullable(record.getTime()).map(Date::getTime).orElse(null);
        }
        return this;
    }

}
