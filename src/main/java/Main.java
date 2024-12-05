import controller.LoginController;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Book;
import model.builder.BookBuilder;
import repository.Book.BookRepositoryMySql;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthentificationServiceImpl;
import view.LoginView;

import java.sql.Connection;
import java.time.LocalDate;

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    public static void main(String[] args)
    {
       launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        final AuthenticationService authenticationService=new AuthentificationServiceImpl(userRepository,rightsRolesRepository);

        final LoginView loginView = new LoginView(primaryStage);


        new LoginController(loginView, authenticationService);

    }
}
