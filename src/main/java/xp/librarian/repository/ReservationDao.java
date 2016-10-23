package xp.librarian.repository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import xp.librarian.model.dto.Reservation;
import xp.librarian.repository.mapper.ReservationMapper;

/**
 * @author xp
 */
@Repository
public class ReservationDao {

    @Autowired
    private ReservationMapper reservationMapper;

    public int add(@NonNull Reservation reservation) {
        return reservationMapper.insert(reservation);
    }

    public int update(@NonNull Reservation where,
                      @NonNull Reservation set) {
        return reservationMapper.update(where, set);
    }

    public Reservation get(@NonNull Integer reservationId) {
        Reservation where = new Reservation();
        where.setId(reservationId);
        return get(where);
    }

    public Reservation get(@NonNull Reservation where) {
        return gets(where, 1, 1).stream()
                .findFirst().orElse(null);
    }

    public List<Reservation> gets(@NonNull Reservation where,
                                  @NonNull Integer page,
                                  @NonNull Integer limits) {
        Integer offset = (page - 1) * limits;
        return reservationMapper.select(where, offset, limits);
    }

}
