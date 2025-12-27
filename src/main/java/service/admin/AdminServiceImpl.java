package service.admin;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.admin.AdminRepository;
import repository.security.RightsRolesRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.EMPLOYEE;

public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;
    private final RightsRolesRepository rightsRolesRepository;
    public AdminServiceImpl(AdminRepository adminRepository,RightsRolesRepository  rightsRolesRepository)
    {
        this.adminRepository=adminRepository;
        this.rightsRolesRepository=rightsRolesRepository;
    }
    @Override
    public List<User> employee_list() {
        return adminRepository.employee_list();
    }

    @Override
    public boolean employee_remove(User user) {
        return adminRepository.employee_remove(user);
    }
    public boolean add_employee(String username,String password){
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(hashPassword(password))
                .setRoles(Collections.singletonList(customerRole))
                .build();
        return adminRepository.add_employee(user);
    }
    public User return_employee(String username)
    {
        return adminRepository.return_employee(username);
    }

    @Override
    public boolean make_pdf(String username) {
        return adminRepository.make_pdf(username);
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
