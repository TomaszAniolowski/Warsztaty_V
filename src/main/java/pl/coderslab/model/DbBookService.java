package pl.coderslab.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DbBookService implements BookService {

    private BookDao bookDao = new BookDao();

    @Override
    public List<Book> getList() {
        List<Model> models = bookDao.loadAll();
        List<Book> books = new ArrayList<>();
        for (Model model : models) {
            books.add((Book) model);
        }
        return books;
    }

    @Override
    public Book getBook(Long id) {
        return (Book) bookDao.loadById(id);
    }

    @Override
    public Book addBook(Book book) {
        bookDao.save(book);
        return bookDao.loadByIsbn(book.getIsbn());
    }

    @Override
    public void removeBook(Book book) {
        bookDao.delete(book);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.save(book);
    }
}
