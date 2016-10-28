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
public class Reservation implements Serializable {

    private static final long serialVersionUID = -4293391434805797781L;

    public Integer id;

    public Integer userId;

    public Integer traceId;

    public enum Status {
        WAITING,    // 等待被归还
        ENABLED,    // 已预订
        CANCELED,   // 预订已取消
        ;
    }

    public Status status;

    public Instant applyingTime;

    public Instant enabledTime;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Reservation)) return false;
        Reservation that = (Reservation) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
