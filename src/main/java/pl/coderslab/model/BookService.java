package pl.coderslab.model;

import java.util.List;

public interface BookService {

    List<Book> getList();
    Book getBook(Long id);
    void setList(List<Book> list);
    Book addBook(Book book);
    void removeBook(Long id);
    void updateBook(Book book);

}
