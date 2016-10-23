package xp.librarian.repository.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import xp.librarian.model.dto.Reservation;

/**
 * @author xp
 */
@Mapper
public interface ReservationMapper {

    int insert(Reservation reservation);

    int update(@Param("where") Reservation where,
               @Param("set") Reservation set);

    List<Reservation> select(@Param("where") Reservation where,
                             @Param("offset") Integer offset,
                             @Param("limits") Integer limits);

}
