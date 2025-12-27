package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.AdminMapper;
import service.admin.AdminService;
import view.AdminDTO.AdminDTO;
import view.AdminView;



public class AdminController {
    private final AdminView adminView;
    private final AdminService adminService;
    public AdminController(AdminView adminView,AdminService adminService)
    {
        this.adminService=adminService;
        this.adminView=adminView;
        this.adminView.addDeleteEmplyeeListener(new deleteButtonListener());
        this.adminView.addSaveEmployeeListener(new saveButtonListener());
        this.adminView.addPDFListener(new addPDFButton());
    }
    private class deleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

            AdminDTO adminDTO=(AdminDTO) adminView.getEmployeeTableView().getSelectionModel().getSelectedItem();
            System.out.println(adminDTO.getUsername());
            if(adminDTO!=null)
            {
                boolean delete_succes=adminService.employee_remove(AdminMapper.convertAdminDTOtoUSER(adminDTO));
                if(delete_succes)
                {
                    adminView.addDisplayAlertMessage("Succes","Delete","Delete");
                    adminView.removeFromObservableList(adminDTO);
                }
                else
                {
                    adminView.addDisplayAlertMessage("Fail","Delete","Delete");
                }
            }
            else
            {
                adminView.addDisplayAlertMessage("EmployeeNULL","Delete","Delete");
            }

        }
    }
    private class addPDFButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            AdminDTO adminDTO=(AdminDTO) adminView.getEmployeeTableView().getSelectionModel().getSelectedItem();
            if(adminDTO!=null)
            {
                boolean pdfsuccess=adminService.make_pdf(AdminMapper.convertAdminDTOtoUSER(adminDTO).getUsername());
                if(pdfsuccess)
                {
                    adminView.addDisplayAlertMessage("Succes","PDF","PDF");
                }
                else
                {
                    adminView.addDisplayAlertMessage("Fail","PDF","PDF");
                }
            }
        }
    }
    private class saveButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            adminService.add_employee(username,password);
            adminView.addInObservableList(AdminMapper.convertUsertoAdminDTO(adminService.return_employee(username)));
        }
    }
}
