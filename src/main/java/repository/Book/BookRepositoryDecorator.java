package repository.Book;

public abstract class BookRepositoryDecorator implements BookRepository{
    protected BookRepository bookRepository;
    public BookRepositoryDecorator(BookRepository b)
    {
        bookRepository=b;
    }
}
