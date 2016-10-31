package xp.librarian.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xp.librarian.model.context.*;

/**
 * @author xp
 */
@RestController
@RequestMapping(value = "/test/")
public class ExceptionController {

    @GetMapping("200")
    public void business() {
        throw new BusinessException(null);
    }

    @GetMapping("401")
    public void unauthorized() {
        throw new UnauthorizedException("test.");
    }

    @GetMapping("403")
    public void forbidden() {
        throw new AccessForbiddenException("test.");
    }

    @GetMapping("404")
    public void resourceNotFound() {
        throw new ResourceNotFoundException("test.");
    }

    @GetMapping("500")
    public void internalServerError() {
        throw new InternalServerException("test.");
    }

}
