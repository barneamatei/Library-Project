package repository.Book;

import model.Book;
import model.builder.BookBuilder;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySql implements BookRepository{
    private Connection connection;
    private String bookname;
    public BookRepositoryMySql(Connection connection)
    {
        this.connection=connection;
    }
    @Override
    public List<Book> findAll() {
        String sql="SELECT * FROM book";
        List <Book> books=new ArrayList<>();

        try{
            Statement statement=connection.createStatement();
            ResultSet resultset=statement.executeQuery(sql);
            while(resultset.next())
            {
                books.add(getBookfromResultSet(resultset));

            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql="SELECT * FROM book WHERE id="+id;
        Optional<Book> book=Optional.empty();
        try{
            Statement statement= connection.createStatement();
            ResultSet resultset=statement.executeQuery(sql);
            if(resultset.next())
            {
                book=Optional.of(getBookfromResultSet(resultset));
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public boolean save(Book book) {
        String sql = "INSERT INTO book (author, title, publishedDate) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Book book) {
        String sql = "DELETE FROM book WHERE author = ? AND title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean decrement(Book book)
    {
        String sql="UPDATE book SET number=number-1 WHERE author=? AND title=?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();
            this.bookname=book.getTitle();
        }catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
       String sql="TRUNCATE TABLE  book";
       try{
           Statement statement=connection.createStatement();
           statement.executeQuery(sql);
       }catch(SQLException e)
       {
           e.printStackTrace();
       }
    }
    private Book getBookfromResultSet(ResultSet resultSet) throws SQLException
    {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                //.setpulisheddate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setNumber(resultSet.getInt("number"))
                .bulid();
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}
