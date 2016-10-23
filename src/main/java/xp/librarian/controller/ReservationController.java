package xp.librarian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.form.ReservationListForm;
import xp.librarian.model.form.ReserveBookForm;
import xp.librarian.model.result.ReservationVM;
import xp.librarian.service.reader.ReservationService;

/**
 * @author xp
 */
@Api(
        description = "Reservations 预约"
)
@RestController
@RequestMapping(value = "/")
public class ReservationController extends BaseController {

    @Autowired
    private ReservationService reservationService;

    @ApiOperation(
            value = "预约租借",
            notes = "只能对 BORROWED 状态的 BookTrace 使用。得到 WAITING 状态的 Reservation。",
            response = ReservationVM.class
    )
    @PostMapping("books/{isbn:[0-9\\-]+}/traces/{traceId}/reserve")
    public Object reserveBook(@PathVariable String isbn, @PathVariable Integer traceId, ReserveBookForm form) {
        form.setIsbn(isbn);
        form.setTraceId(traceId);
        return renderForEntity(reservationService.reserveBook(getAccount(), form));
    }

    @ApiOperation(
            value = "查看预约列表",
            notes = "",
            response = ReservationVM.class,
            responseContainer = "List"
    )
    @GetMapping("users/self/reservations/")
    public Object getReservations(ReservationListForm form, PagingForm paging) {
        return renderForEntities(reservationService.getReservations(getAccount(), form, paging));
    }

    @ApiOperation(
            value = "查看预约详情",
            notes = "目前跟列表里的没什么区别。",
            response = ReservationVM.class
    )
    @GetMapping("users/self/reservations/{reservationId}/")
    public Object getReservation(@PathVariable Integer reservationId) {
        return renderForEntity(reservationService.getReservation(getAccount(), reservationId));
    }

    @ApiOperation(
            value = "取消预约",
            notes = "只能对 WAITING 状态的 Reservation 使用，将转变为 CANCELED 状态。",
            response = ReservationVM.class
    )
    @PostMapping("users/self/reservations/{reservationId}/cancel")
    public Object cancelReservation(@PathVariable Integer reservationId) {
        reservationService.cancelReservation(getAccount(), reservationId);
        return renderForAction(true);
    }

}
