package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {
    private final Connection connection;

    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book;";

        List<Book> books = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = " + id;

        Optional<Book> book = Optional.empty();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                book = Optional.of(getBookFromResultSet(resultSet));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public boolean save(Book book) {
        String newSql = "INSERT INTO book (author, title, stock) VALUES (?, ?, 1);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public boolean delete(Book book) {
        String newSql = "DELETE FROM book WHERE author =\'" + book.getAuthor() + "\' AND title =\'" + book.getTitle() + "\';";

        try{
           Statement statement = connection.createStatement();
           statement.executeUpdate(newSql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM book WHERE id >= 0;";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();

        }

    }

    @Override
    public boolean sell(Book book) {
        String sql = "UPDATE book SET stock = stock - 1 WHERE author = '" + book.getAuthor()
                + "' AND title = '" + book.getTitle() + "' AND stock > 0;";

        try {
            Statement statement = connection.createStatement();
            int rowsUpdated = statement.executeUpdate(sql);

            if (rowsUpdated == 0) {
                System.out.println("Stock is empty or book not found for: "
                        + book.getTitle() + " by " + book.getAuthor());
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        java.sql.Date publishedDate = resultSet.getDate("publishedDate");

        if (publishedDate != null) {
            return new BookBuilder()
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(publishedDate.toLocalDate())
                    .setStock(resultSet.getInt("stock"))
                    .build();
        } else {
            return new BookBuilder()
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(LocalDate.of(1900, 1, 1))
                    .setStock(resultSet.getInt("stock"))
                    .build();
        }
    }



}
