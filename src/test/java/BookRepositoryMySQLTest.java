import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.BookRepositoryMySQL;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BookRepositoryMySQLTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private BookRepositoryMySQL bookRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        bookRepository = new BookRepositoryMySQL(connection);
    }

    @Test
    public void testFindAll() throws SQLException {
        String sql = "SELECT * FROM book;";
        when(connection.createStatement()).thenReturn(mock(Statement.class));
        Statement statement = connection.createStatement();
        when(statement.executeQuery(sql)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false); // only one row
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("title")).thenReturn("Test Title");
        when(resultSet.getString("author")).thenReturn("Test Author");
        when(resultSet.getDate("publishedDate")).thenReturn(Date.valueOf(LocalDate.of(2023, 1, 1)));

        List<Book> books = bookRepository.findAll();

        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
        assertEquals("Test Author", books.get(0).getAuthor());
    }

    @Test
    public void testFindById() throws SQLException {
        String sql = "SELECT * FROM book WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("title")).thenReturn("Test Title");
        when(resultSet.getString("author")).thenReturn("Test Author");
        when(resultSet.getDate("publishedDate")).thenReturn(Date.valueOf(LocalDate.of(2023, 1, 1)));

        Optional<Book> book = bookRepository.findById(1L);

        assertTrue(book.isPresent());
        assertEquals("Test Title", book.get().getTitle());
        assertEquals("Test Author", book.get().getAuthor());
    }

    @Test
    public void testSave() throws SQLException {
        String sql = "INSERT INTO book VALUES(null, ?, ?, ?)";
        Book book = new BookBuilder()
                .setTitle("New Book")
                .setAuthor("New Author")
                .setPublishedDate(LocalDate.of(2023, 1, 1))
                .build();

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean isSaved = bookRepository.save(book);

        assertTrue(isSaved);
        verify(preparedStatement, times(1)).setString(1, "New Author");
        verify(preparedStatement, times(1)).setString(2, "New Book");
        verify(preparedStatement, times(1)).setDate(3, Date.valueOf(LocalDate.of(2023, 1, 1)));
    }

    @Test
    public void testDelete() throws SQLException {
        String sql = "DELETE FROM book WHERE author = ? AND title = ?";
        Book book = new BookBuilder()
                .setTitle("Delete Book")
                .setAuthor("Delete Author")
                .build();

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean isDeleted = bookRepository.delete(book);

        assertTrue(isDeleted);
        verify(preparedStatement, times(1)).setString(1, "Delete Author");
        verify(preparedStatement, times(1)).setString(2, "Delete Book");
    }

    @Test
    public void testRemoveAll() throws SQLException {
        String sql = "DELETE FROM book";
        Statement statement = mock(Statement.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeUpdate(sql)).thenReturn(1);

        bookRepository.removeAll();

        verify(statement, times(1)).executeUpdate(sql);
    }
}

