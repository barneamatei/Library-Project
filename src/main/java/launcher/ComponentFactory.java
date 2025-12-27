package launcher;


import controller.BookControler;
import database.DataBaseConnectionFactory;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import mapper.BookMapper;
import model.Book;
import repository.Book.BookRepository;
import repository.Book.BookRepositoryMySql;

import repository.sale.SaleRepository;
import repository.sale.SaleRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.sale.SaleService;
import view.BookDTO.BookDTO;
import view.BookView;

import java.sql.Connection;
import java.util.List;

public class ComponentFactory {

    private final BookView bookview;
    private final BookControler bookcontroler;

    private final BookRepository bookRepository;

    private final BookService bookService;

    private SaleRepository saleRepository;
    private RightsRolesRepository rightsRolesRepository;
    private UserRepository userRepository;

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
        Connection connection= DataBaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository=new RightsRolesRepositoryMySQL(connection);
        this.userRepository=new UserRepositoryMySQL(connection,rightsRolesRepository);
        this.bookRepository=new BookRepositoryMySql(connection);
        this.saleRepository=new SaleRepositoryMySQL(bookRepository,userRepository,connection);
        this.bookService=new BookServiceImpl(bookRepository,saleRepository);
        List<BookDTO> bookDTOS= BookMapper.convertBookLIsttoBookDTOlIST(bookService.finaAll());
        displayBookList(bookDTOS);
        this.bookview=new BookView(primarystage,bookDTOS);
        this.bookcontroler = new BookControler(bookview, bookService);


    }


    public BookView getBookview() {
        return bookview;
    }

    public BookControler getBookcontroler() {
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
    public void displayBookList(List<BookDTO> bookList) {
        for (BookDTO book : bookList) {
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Number: " + book.getNumber());
            System.out.println("-----------------------------");
        }
    }
}