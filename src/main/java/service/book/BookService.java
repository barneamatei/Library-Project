package service.book;

import model.Book;

import java.util.List;

public interface BookService {
    List<Book> finaAll();
    boolean decrement(Book book);
    Book findById(Long Id);
    boolean save(Book book);
    boolean delete(Book book);
    int getAgeOfBook(Long Id);
    boolean add_sale();
}
