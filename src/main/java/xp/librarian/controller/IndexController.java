package xp.librarian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author xp
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping("")
    public Object index() {
        return "Welcome to Librarian API.";
    }

}
