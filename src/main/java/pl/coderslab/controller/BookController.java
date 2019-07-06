package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.Book;
import pl.coderslab.model.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(@Qualifier("dbBookService") BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id){
        return bookService.getBook(id);
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookService.getList();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public void updateBook(@RequestBody Book book){
        bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public void removeBook(@PathVariable Long id) {
        bookService.removeBook(bookService.getBook(id));
    }

}
