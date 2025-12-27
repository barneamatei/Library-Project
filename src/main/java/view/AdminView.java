package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.AdminDTO.AdminDTO;
import view.BookDTO.BookDTO;



import java.util.List;

public class AdminView {
    private TableView employeeTableView;
    private TextField emailtextfield;
    private TextField passwordtextfield;
    private  final ObservableList<AdminDTO> userobservablelist;
    private Label emaillabel;
    private Label passwordlabel;
    private Button savebutton;
    private Button deletebutton;
    private Button pdfbutton;
    public AdminView(Stage primaryStage, List<AdminDTO> users)
    {
        primaryStage.setTitle("Admin Page");
        GridPane gridpane=new GridPane();
        initializeGridPane(gridpane);
        Scene scene=new Scene(gridpane,720,480);
        primaryStage.setScene(scene);
        userobservablelist= FXCollections.observableArrayList(users);
        initSaveOptions(gridpane);
        inittableview(gridpane);
        primaryStage.show();
    }
    public void removeFromObservableList(AdminDTO adminDTO)
    {

        userobservablelist.remove(adminDTO);
    }
    public void addInObservableList(AdminDTO adminDTO)
    {
        userobservablelist.add(adminDTO);
    }
    private void initializeGridPane(GridPane gridpane)
    {
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        gridpane.setPadding(new Insets(25,25,25,25));
    }
    private void initSaveOptions(GridPane gridpane)
    {
        emaillabel=new Label("Email");
        gridpane.add(emaillabel,1,1);
        emailtextfield=new TextField();
        gridpane.add(emailtextfield,2,1);
        passwordlabel=new Label("Password");
        gridpane.add(passwordlabel,3,1);
        passwordtextfield=new TextField();
        gridpane.add(passwordtextfield,4,1);
        savebutton=new Button("save");
        gridpane.add(savebutton,6,1);
        deletebutton = new Button("delete");
        gridpane.add(deletebutton, 7, 1);
        pdfbutton= new Button("PDF");
        gridpane.add(pdfbutton,8,1);
    }
    private void inittableview(GridPane gridpane)
    {
        employeeTableView= new TableView<BookDTO>();
        employeeTableView.setPlaceholder(new javafx.scene.control.Label("No employee to dispaly"));
        TableColumn<BookDTO,String> titlecolumnn=new TableColumn<BookDTO,String>("id");
        titlecolumnn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<BookDTO,String> authorColumn=new TableColumn<BookDTO,String>("username");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        employeeTableView.getColumns().addAll(titlecolumnn,authorColumn);
        employeeTableView.setItems(userobservablelist);
        gridpane.add(employeeTableView,0,0,5,1);
    }
    public TableView getEmployeeTableView()
    {
        return employeeTableView;
    }

    public void addDeleteEmplyeeListener(EventHandler<ActionEvent> deleteButtonListener)
    {
        deletebutton.setOnAction(deleteButtonListener);
    }
    public void addSaveEmployeeListener(EventHandler<ActionEvent> saveButtonListener)
    {
        savebutton.setOnAction(saveButtonListener);
    }
    public void addPDFListener(EventHandler<ActionEvent> pdfButtonListner)
    {
        pdfbutton.setOnAction(pdfButtonListner);
    }
    public void addDisplayAlertMessage(String title,String header,String content)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public String getUsername() {

        return emailtextfield.getText();
    }

    public String getPassword() {

        return  passwordtextfield.getText();
    }

}
