package xp.librarian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import xp.librarian.model.form.LendBookForm;
import xp.librarian.model.form.LendListForm;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.LendVM;
import xp.librarian.service.reader.LendService;

/**
 * @author xp
 */
@Api(
        description = "Lends 租借"
)
@RestController
@RequestMapping(value = "/")
public class LendController extends BaseController {

    @Autowired
    private LendService lendService;

    @ApiOperation(
            value = "申请租借",
            notes = "只能对 NORMAL 状态的 BookTrace 使用，将使其转变为 LOCKED 状态。最后得到 APPLYING 状态的 Lend。",
            response = LendVM.class
    )
    @PostMapping("books/{isbn:[0-9\\-]+}/traces/{traceId}/lend")
    public Object lendBook(@PathVariable String isbn, @PathVariable Integer traceId, LendBookForm form) {
        form.setIsbn(isbn);
        form.setTraceId(traceId);
        return renderForEntity(lendService.lendBook(getAccount(), form));
    }

    @ApiOperation(
            value = "获取租借列表",
            notes = "",
            response = LendVM.class,
            responseContainer = "List"
    )
    @GetMapping("users/self/lends/")
    public Object getLends(LendListForm form, PagingForm paging) {
        return renderForEntities(lendService.getLends(getAccount(), form, paging));
    }

    @ApiOperation(
            value = "获取租借记录",
            notes = "目前跟列表里的没什么区别。",
            response = LendVM.class
    )
    @GetMapping("users/self/lends/{lendId}/")
    public Object getLend(@PathVariable Integer lendId) {
        return renderForEntity(lendService.getLend(getAccount(), lendId));
    }

    @ApiOperation(
            value = "取消租借申请",
            notes = "只能对 APPLYING 状态的 Lend 使用，将转变为 CANCELED 状态。相应的 BookTrace 里 lendId 清零，但状态仍然为 LOCKED，它将由定时任务转变回 NORMAL 状态。"
    )
    @PostMapping("users/self/lends/{lendId}/cancel")
    public Object cancelLend(@PathVariable Integer lendId) {
        lendService.cancelLend(getAccount(), lendId);
        return renderForAction(true);
    }

    @ApiOperation(
            value = "续租",
            notes = "只能对 ACTIVE 状态的 Lend 使用，将 appointedTime 延后 30 天。"
    )
    @PostMapping("users/self/lends/{lendId}/renew")
    public Object renewLend(@PathVariable Integer lendId) {
        return renderForEntity(lendService.renewLend(getAccount(), lendId));
    }

}
