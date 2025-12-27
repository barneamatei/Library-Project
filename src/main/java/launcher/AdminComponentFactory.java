package launcher;

import controller.AdminController;
import database.DataBaseConnectionFactory;
import javafx.stage.Stage;
import mapper.AdminMapper;
import model.User;
import repository.admin.AdminRepository;
import repository.admin.AdminRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.admin.AdminService;
import service.admin.AdminServiceImpl;
import view.AdminDTO.AdminDTO;
import view.AdminView;
import view.BookDTO.BookDTO;
import view.LoginView;

import java.sql.Connection;
import java.util.List;


public class AdminComponentFactory {
    private final AdminView adminView;
    private static Stage stage;
    private final AdminService adminService;
    private final AdminRepository adminRepository;
    private static AdminComponentFactory instance;
    private final RightsRolesRepository rightsRolesRepository;

    private final AdminController adminController;

    public static AdminComponentFactory getInstance(Boolean Componentsfortest, Stage aStage) {
        if (instance == null) {
            synchronized (AdminComponentFactory.class)
            {
                instance=new AdminComponentFactory(Componentsfortest,aStage);
            }
        }

        return instance;
    }
    public AdminComponentFactory(Boolean ComponentsForTest,Stage stage)
    {
        Connection connection= DataBaseConnectionFactory.getConnectionWrapper(ComponentsForTest).getConnection();
        this.rightsRolesRepository=new RightsRolesRepositoryMySQL(connection);
        this.adminRepository=new AdminRepositoryMySQL(connection,rightsRolesRepository);
        this.adminService=new AdminServiceImpl(adminRepository,rightsRolesRepository);
        List<AdminDTO> users= AdminMapper.convertUserListtoAdminDTOLIST(adminService.employee_list());
        this.adminView=new AdminView(stage,users);
        this.adminController=new AdminController(adminView,adminService);
    }
    public void displayBookList(List<User> bookList) {
        for (User book : bookList) {
            System.out.println("Username: " + book.getUsername());
            System.out.println("Id: " + book.getId());
            System.out.println("-----------------------------");
        }
    }
}
