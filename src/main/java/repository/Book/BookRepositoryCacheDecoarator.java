package repository.Book;

import model.Book;

import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecoarator extends BookRepositoryDecorator{
    private Cache<Book> cache;
    public BookRepositoryCacheDecoarator(BookRepository bookRepository,Cache<Book> cache)
    {
        super(bookRepository);
        this.cache=cache;
    }
    @Override
    public List<Book> findAll() {
        if(cache.hasResult())
        {
            return cache.load();
        }
        List<Book> books=bookRepository.findAll();
        cache.save(books);
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        if(cache.hasResult()){
            return cache.load().stream()
                    .filter(it->it.getId().equals(id))
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Book book) {
        cache.invaliadteCache();
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        cache.invaliadteCache();
        return bookRepository.delete(book);
    }

    @Override
    public void removeAll() {
       cache.invaliadteCache();
        bookRepository.removeAll();
    }

    @Override
    public String getBookname() {
        return null;
    }

    public boolean decrement(Book book)
    {
        return true;
    }
}
