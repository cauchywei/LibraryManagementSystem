package xp.librarian.repository;

import java.util.*;

import xp.librarian.model.dto.Reservation;

/**
 * @author xp
 */
public interface ReservationDao {

    int add(Reservation reservation);

    int update(Reservation where, Reservation set);

    Reservation get(Integer reservationId);

    Reservation get(Reservation where);

    List<Reservation> gets(Reservation where, Integer page, Integer limits);

}
