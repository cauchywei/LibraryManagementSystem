package xp.librarian.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xp
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity index() {
        return new ResponseEntity<>("Welcome to Librarian API.", HttpStatus.OK);
    }

}
