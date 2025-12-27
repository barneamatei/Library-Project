package service.book;

import model.Book;
import repository.Book.BookRepository;
import repository.sale.SaleRepository;
import service.book.BookService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookRepository bookrepository;
    private final SaleRepository saleRepository;
    public BookServiceImpl(BookRepository b,SaleRepository s)
    {
        this.saleRepository=s;
        this.bookrepository=b;
    }
    @Override
    public List<Book> finaAll() {
        return bookrepository.findAll();
    }

    @Override
    public Book findById(Long Id) {
        return bookrepository.findById(Id).orElseThrow(()-> new IllegalArgumentException("Book with id : %d ws not found"));
    }

    @Override
    public boolean save(Book book) {

        return bookrepository.save(book);
    }

    @Override
    public boolean delete(Book book) {

        return bookrepository.delete(book);
    }
    public boolean decrement(Book book)
    {
        return bookrepository.decrement(book);
    }
    @Override
    public int getAgeOfBook(Long Id) {
        Book book=this.findById(Id);
        LocalDate now =LocalDate.now();
        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(),now);
    }

    @Override
    public boolean add_sale() {
        return saleRepository.add_sale();
    }
}
