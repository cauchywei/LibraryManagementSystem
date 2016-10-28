package xp.librarian.model.dto;

import java.io.*;
import java.time.*;
import java.util.*;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author xp
 */
@Getter
@Setter
@Accessors(chain = true)
public class BookTrace implements Serializable {

    private static final long serialVersionUID = -7884633389893650476L;

    public Integer id;

    public String isbn;

    public enum Status {
        NORMAL,     // 在架上
        LOCKED,     // 已被申请借书，尚未处理
        BORROWED,   // 已借出
        DELETED,    // 已被管理员删除
        ;
    }

    public Status status;

    public String location;

    public Integer lendId;

    public Instant createTime;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BookTrace)) return false;
        BookTrace trace = (BookTrace) object;
        return Objects.equals(id, trace.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
