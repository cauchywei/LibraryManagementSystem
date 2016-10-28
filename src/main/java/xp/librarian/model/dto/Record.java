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
public class Record implements Serializable {

    private static final long serialVersionUID = 8338137113945623443L;

    public Integer id;

    public Integer userId;

    public Integer traceId;

    public Action action;

    public Object payload;

    public Instant time;

    public static Record apply(Lend lend) {
        Record record = new Record();
        record.setAction(Action.APPLY);
        record.setUserId(lend.getUserId());
        record.setTraceId(lend.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record cancelApplication(Lend lend) {
        Record record = new Record();
        record.setAction(Action.CANCEL_APPLICATION);
        record.setUserId(lend.getUserId());
        record.setTraceId(lend.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record renewLend(Lend lend) {
        Record record = new Record();
        record.setAction(Action.RENEW);
        record.setUserId(lend.getUserId());
        record.setTraceId(lend.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record reserve(Reservation reservation) {
        Record record = new Record();
        record.setAction(Action.RESERVE);
        record.setUserId(reservation.getUserId());
        record.setTraceId(reservation.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record cancelReservation(Reservation reservation) {
        Record record = new Record();
        record.setAction(Action.CANCEL_RESERVATION);
        record.setUserId(reservation.getUserId());
        record.setTraceId(reservation.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record accept(Lend lend) {
        Record record = new Record();
        record.setAction(Action.ACCEPT);
        record.setUserId(lend.getUserId());
        record.setTraceId(lend.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record reject(Lend lend) {
        Record record = new Record();
        record.setAction(Action.REJECT);
        record.setUserId(lend.getUserId());
        record.setTraceId(lend.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record confirmReturned(Lend lend) {
        Record record = new Record();
        record.setAction(Action.CONFIRM_RETURNED);
        record.setUserId(lend.getUserId());
        record.setTraceId(lend.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record confirmDisabled(Lend lend) {
        Record record = new Record();
        record.setAction(Action.CONFIRM_DISABLED);
        record.setUserId(lend.getUserId());
        record.setTraceId(lend.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record expired(Lend lend) {
        Record record = new Record();
        record.setAction(Action.EXPIRED);
        record.setUserId(lend.getUserId());
        record.setTraceId(lend.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record reserved(Reservation reservation) {
        Record record = new Record();
        record.setAction(Action.RESERVED);
        record.setUserId(reservation.getUserId());
        record.setTraceId(reservation.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    public static Record late(Lend lend) {
        Record record = new Record();
        record.setAction(Action.LATE);
        record.setUserId(lend.getUserId());
        record.setTraceId(lend.getTraceId());
        record.setTime(Instant.now());
        return record;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Record)) return false;
        Record record = (Record) object;
        return Objects.equals(id, record.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
