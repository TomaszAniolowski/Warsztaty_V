package pl.coderslab.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemoryBookService {
    private List<Book> list;

    public MemoryBookService() {
        list = new ArrayList<>();
        list.add(new Book(1L, "9788324631766", "Thinking in Java", "Bruce Eckel",
                "Helion", "programming"));
        list.add(new Book(2L, "9788324627738", "Rusz glowa, Java.",
                "Sierra Kathy, Bates Bert", "Helion", "programming"));
        list.add(new Book(3L, "9780130819338", "Java 2. Podstawy",
                "Cay Horstmann, Gary Cornell", "Helion", "programming"));
    }

    public List<Book> getList() {
        return list;
    }

    public Book getBook(Long id) {
        return list.stream().filter(b -> b.getId() == id).findFirst().get();
    }

    public void setList(List<Book> list) {
        this.list = list;
    }

    public void addBook (Book book) {
        list.add(book);
    }

    public void removeBook (Long id) {
        list = list.stream()
                .filter(b -> b.getId() != id)
                .collect(Collectors.toList());
    }

    public void updateBook (Book book) {
        list = list.stream()
                .filter(b -> b.getId() == book.getId())
                .map(b -> b = book)
                .collect(Collectors.toList());
    }
}
