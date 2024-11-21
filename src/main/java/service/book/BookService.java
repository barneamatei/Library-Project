package service.book;
import java.util.List;
import model.Book;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    boolean save(Book book);
    boolean delete(Book book);
    int getAgeOfBook(Long id);

}
