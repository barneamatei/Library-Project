package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import launcher.AdminComponentFactory;
import launcher.ComponentFactory;
import launcher.LoginComponentFactory;
import model.User;

import model.validator.Notification;
import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.LoginView;

import java.util.EventListener;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;



    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            boolean succes_sale;
            Notification<User> LoginNotification = authenticationService.login(username, password);

            if (LoginNotification.hasErrors()) {
                loginView.setActionTargetText(LoginNotification.getFormattedErrors());
            } else {
                  if(authenticationService.returnRole(username)==2)
                  {
                      ComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(),LoginComponentFactory.getStage());
                  }
                  else if(authenticationService.returnRole(username)==1)
                  {
                      AdminComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(),LoginComponentFactory.getStage());
                  }

                loginView.setActionTargetText("LogIn Successfull! ");

            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register succesful");
            }
        }
    }
}