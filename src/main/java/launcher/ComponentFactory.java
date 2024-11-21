package launcher;

import controller.BookController;
import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImplementation;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;
import view.BookView;

import java.sql.Connection;
import java.util.List;

public class ComponentFactory {

    private final BookView bookview;
    private final BookController bookcontroler;

    private final BookRepository bookRepository;

    private final BookService bookService;

    private static ComponentFactory instance;

    public static ComponentFactory getInstance(Boolean componentsForTest, Stage primarystage){
        if(instance==null)
        {
            synchronized (ComponentFactory.class)
            {
                instance = new ComponentFactory(componentsForTest,primarystage);
            }
        }
        return instance;
    }

    public ComponentFactory(Boolean componentsForTest, Stage primarystage){
        Connection connection= DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository=new BookRepositoryMySQL(connection);
        this.bookService=new BookServiceImplementation(bookRepository);
        List<BookDTO> bookDTOS= BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookview=new BookView(primarystage,bookDTOS);
        this.bookcontroler = new BookController(bookview, bookService);


    }


    public BookView getBookview() {
        return bookview;
    }

    public BookController getBookcontroler() {
        return bookcontroler;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    public static ComponentFactory getInstance() {
        return instance;
    }
}
