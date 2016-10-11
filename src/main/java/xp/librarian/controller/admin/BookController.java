package xp.librarian.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import xp.librarian.controller.BaseController;
import xp.librarian.model.form.BookAddForm;
import xp.librarian.model.form.BookUpdateForm;
import xp.librarian.model.form.PagingForm;
import xp.librarian.service.admin.BookService;

/**
 * @author xp
 */
@Controller("adminBookController")
@RequestMapping(value = "/admin/books/")
public class BookController extends BaseController {

    @Autowired
    private BookService bookService;

    @PostMapping("add")
    public ResponseEntity addBook(BookAddForm form) {
        return renderForEntity(bookService.add(form));
    }

    @PostMapping("{ISBN:[0-9\\-]+}/update")
    public ResponseEntity updateBook(BookUpdateForm form) {
        return renderForEntity(bookService.update(form));
    }

    @PostMapping("{ISBN:[0-9\\-]+}/delete")
    public ResponseEntity deleteBook(@PathVariable String ISBN) {
        return renderForAction(bookService.delete(ISBN));
    }

    @GetMapping("")
    public ResponseEntity getBookList(PagingForm paging) {
        return renderForEntities(bookService.getList(paging));
    }

    @GetMapping("{ISBN:[0-9\\-]+}/")
    public ResponseEntity getBook(@PathVariable String ISBN) {
        return renderForEntity(bookService.get(ISBN));
    }

}
