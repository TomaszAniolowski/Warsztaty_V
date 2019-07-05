package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.Book;
import pl.coderslab.model.MemoryBookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private MemoryBookService memoryBookService;

    @Autowired
    public BookController(MemoryBookService memoryBookService){
        this.memoryBookService = memoryBookService;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id){
        return memoryBookService.getBook(id);
    }

    @GetMapping
    public List<Book> getBooks(){
        List<Book> books = memoryBookService.getList();

        return books;
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        memoryBookService.addBook(book);
    }

    @PutMapping("/{id}")
    public void updateBook(@RequestBody Book book){
        memoryBookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public void removeBook(Long id) {
        memoryBookService.removeBook(id);
    }

}
