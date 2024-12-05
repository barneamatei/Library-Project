package service.admin;

import model.User;

import java.util.List;

public interface AdminService {
    public List<User> employee_list();
    public boolean employee_remove(User user);
    public boolean add_employee(String username,String password);
    public User return_employee(String username);
    public boolean make_pdf(String username);
}
