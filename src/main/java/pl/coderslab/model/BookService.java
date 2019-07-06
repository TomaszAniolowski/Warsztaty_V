package pl.coderslab.model;

import java.util.List;

public interface BookService {

    List<Book> getList();
    Book getBook(Long id);
    Book addBook(Book book);
    void removeBook(Book book);
    void updateBook(Book book);

}
