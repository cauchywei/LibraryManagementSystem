package xp.librarian.model.form;

import java.io.*;

import lombok.Data;
import xp.librarian.model.dto.Reservation;

/**
 * @author xp
 */
@Data
public class ReservationListForm implements Serializable {

    private static final long serialVersionUID = -7030940184733840535L;

    private Reservation.Status status;

}
