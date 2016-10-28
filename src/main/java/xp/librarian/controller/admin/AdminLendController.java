package xp.librarian.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import xp.librarian.controller.BaseController;
import xp.librarian.model.form.AdminLendListForm;
import xp.librarian.model.form.PagingForm;
import xp.librarian.model.result.LendVM;
import xp.librarian.service.admin.LendService;

/**
 * @author xp
 */
@Api(
        description = "Admin::Lends 租借管理"
)
@Controller
@RequestMapping(value = "/admin/")
public class AdminLendController extends BaseController {

    @Autowired
    private LendService lendService;

    @ApiOperation(
            value = "查看租借列表",
            notes = "所有人的。",
            response = LendVM.class,
            responseContainer = "List"
    )
    @GetMapping("lends/")
    public Object getLends(AdminLendListForm form, PagingForm paging) {
        return renderForEntities(lendService.getLends(form, paging));
    }

    @ApiOperation(
            value = "查看租借详情",
            notes = "使用这个接口实现单本借书、还书功能。",
            response = LendVM.class
    )
    @GetMapping("lends/{lendId}/")
    public Object getLend(@PathVariable Integer lendId) {
        return renderForEntity(lendService.getLend(lendId));
    }

    @ApiOperation(
            value = "接受租借申请",
            notes = "将使 Lend 从 APPLYING 改变到 ACTIVE 状态，使 BookTrace 从 LOCKED 改变到 BORROWED 状态。"
    )
    @PostMapping("lends/{lendId}/accept")
    public Object acceptLend(@PathVariable Integer lendId) {
        lendService.acceptLend(lendId);
        return renderForAction(true);
    }

    @ApiOperation(
            value = "拒绝租借申请",
            notes = "将使 Lend 从 APPLYING 改变到 REJECTED 状态，使 BookTrace 的 lendId 清零，剩下的交给定时任务。"
    )
    @PostMapping("lends/{lendId}/reject")
    public Object rejectLend(@PathVariable Integer lendId) {
        lendService.rejectLend(lendId);
        return renderForAction(true);
    }

    @ApiOperation(
            value = "确认归还",
            notes = "将使 Lend 从 ACTIVE 改变到 RETURNED 状态，使 BookTrace 从 BORROWED 改变到 LOCKED 状态，剩下的交给定时任务。"
    )
    @PostMapping("lends/{lendId}/confirmReturned")
    public Object confirmLendReturned(@PathVariable Integer lendId) {
        lendService.confirmLendReturned(lendId);
        return renderForAction(true);
    }

    @ApiOperation(
            value = "确认无法归还",
            notes = "将使 Lend 从 ACTIVE 改变到 DISABLED 状态，使 BookTrace 从 BORROWED 改变到 DELETED 状态。"
    )
    @PostMapping("lends/{lendId}/confirmDisabled")
    public Object confirmLendDisabled(@PathVariable Integer lendId) {
        lendService.confirmLendDisabled(lendId);
        return renderForAction(true);
    }

}
