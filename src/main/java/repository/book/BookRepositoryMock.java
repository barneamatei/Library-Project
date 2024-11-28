package repository.book;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository {
    private List<Book> books;

    public BookRepositoryMock() {
        books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public void removeAll() {
        books.clear();

    }

    @Override
    public boolean sell(Book book) {
        for (Book b : books) {
            if (b.getId().equals(book.getId())) {
                if (b.getStock() > 0) {
                    b.setStock(b.getStock() - 1);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


}
