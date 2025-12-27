package service.user;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

import static database.Constants.Roles.CUSTOMER;

public class AuthentificationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthentificationServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {



        Role customerRole = rightsRolesRepository.findRoleByTitle(CUSTOMER);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(customerRole))
                .build();
        UserValidator userValidator= new UserValidator(user);
        boolean uservalid=userValidator.validate();
        Notification<Boolean> userRegisterNotification=new Notification<>();
        if(!uservalid)
        {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        }
        else{
            user.setPassword(hashPassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        //String encodedPassword=hashPassword(password);

        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, hashPassword(password));
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    @Override
    public int returnRole(String username) {
        return userRepository.returnID(username);
    }

    @Override
    public boolean userSales(String username,String bookname) {
        return userRepository.usersSales(username,bookname);
    }




    private String hashPassword(String password) {
        try {
            // Sercured Hash Algorithm - 256
            // 1 byte = 8 biți
            // 1 byte = 1 char
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}