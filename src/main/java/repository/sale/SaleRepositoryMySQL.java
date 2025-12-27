package repository.sale;

import repository.Book.BookRepository;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaleRepositoryMySQL implements SaleRepository{
private BookRepository bookRepository;
private UserRepository userRepository;
private Connection connection;
public SaleRepositoryMySQL(BookRepository bookRepository, UserRepository userRepository,Connection connection1)
{
    this.bookRepository=bookRepository;
    this.userRepository=userRepository;
    this.connection=connection1;
}
    @Override
    public boolean add_sale() {
        String sql = "INSERT INTO sales (username, bookname) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userRepository.getUsername());
            preparedStatement.setString(2, bookRepository.getBookname());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
