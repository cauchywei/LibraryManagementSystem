package xp.librarian.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import xp.librarian.model.form.BookSearchForm;
import xp.librarian.model.form.PagingForm;
import xp.librarian.service.BookService;
import xp.librarian.service.BorrowService;

/**
 * @author xp
 */
@RestController
@RequestMapping(value = "/books/")
public class BookController extends BaseController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowService borrowService;

    @GetMapping("search")
    public ResponseEntity search(@Valid BookSearchForm form) {
        return renderForEntities(bookService.search(form));
    }

    @GetMapping("{ISBN:[0-9\\-]+}/")
    public ResponseEntity getBook(@PathVariable String ISBN) {
        return renderForEntity(bookService.get(ISBN));
    }

    @PostMapping("{ISBN:[0-9\\-]+}/borrow")
    public ResponseEntity borrowBook(@PathVariable String ISBN) {
        return renderForAction(borrowService.borrow(getAccount(), ISBN));
    }

    @PostMapping("{ISBN:[0-9\\-]+}/return")
    public ResponseEntity revertBook(@PathVariable String ISBN) {
        return renderForAction(borrowService.revert(getAccount(), ISBN));
    }

}
