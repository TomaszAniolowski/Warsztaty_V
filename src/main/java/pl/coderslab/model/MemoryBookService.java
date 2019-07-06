package pl.coderslab.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
public class MemoryBookService implements BookService {
    private static Long currentId = 1L;
    private List<Book> list;

    public MemoryBookService() {
        list = new ArrayList<>();
        list.add(new Book(currentId++, "9788324631766", "Thinking in Java", "Bruce Eckel",
                "Helion", "programming"));
        list.add(new Book(currentId++, "9788324627738", "Rusz glowa, Java.",
                "Sierra Kathy, Bates Bert", "Helion", "programming"));
        list.add(new Book(currentId++, "9780130819338", "Java 2. Podstawy",
                "Cay Horstmann, Gary Cornell", "Helion", "programming"));
    }

    public List<Book> getList() {
        return list;
    }

    public Book getBook(Long id) {
        return list.stream().filter(b -> b.getId().equals(id)).findFirst().get();
    }

    public Book addBook (Book book) {
        book.setId(currentId++);
        list.add(book);
        return book;
    }

    public void removeBook (Book book) {
        list = list.stream()
                .filter(b -> b.getId().equals(book.getId()))
                .collect(Collectors.toList());
    }

    public void updateBook (Book book) {
        ListIterator<Book> bookIterator = list.listIterator();
        while (bookIterator.hasNext()){
            Book tempBook = bookIterator.next();
            if (tempBook.getId().equals(book.getId())){
                bookIterator.set(book);
            }
        }
    }
}
