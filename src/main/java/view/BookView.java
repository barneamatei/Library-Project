package view;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Book;
import view.BookDTO.BookDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.List;

public class BookView {
    private TableView bookTableView;
    private final ObservableList<BookDTO> bookobservablelist;
    private TextField authortextfield;
    private TextField titletextfield;
    private Label authorlabel;
    private Label titleLablel=new Label("title");
    private Button savebutton;
    private Button deletebutton;
    private Button salebutton;
    public BookView(Stage primaryStage, List<BookDTO> books)
    {
        primaryStage.setTitle("Library");
        GridPane gridpane=new GridPane();
        initializeGridPane(gridpane);
        Scene scene=new Scene(gridpane,720,480);
        primaryStage.setScene(scene);
        bookobservablelist= FXCollections.observableArrayList(books);
        initSaveOptions(gridpane);
        inittableview(gridpane);
        primaryStage.show();
    }
    private void initializeGridPane(GridPane gridpane)
    {
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        gridpane.setPadding(new Insets(25,25,25,25));
    }
    private void inittableview(GridPane gridpane)
    {
      bookTableView= new TableView<BookDTO>();
      bookTableView.setPlaceholder(new javafx.scene.control.Label("No books to display"));
        TableColumn<BookDTO,String> titlecolumnn=new TableColumn<BookDTO,String>("title");
       titlecolumnn.setCellValueFactory(new PropertyValueFactory<>("title"));
       TableColumn<BookDTO,String> authorColumn=new TableColumn<BookDTO,String>("author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<BookDTO,String> numbercolumn=new TableColumn<>("number");
        numbercolumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        bookTableView.getColumns().addAll(titlecolumnn,authorColumn,numbercolumn);
        bookTableView.setItems(bookobservablelist);
        gridpane.add(bookTableView,0,0,5,1);
    }
    private void initSaveOptions(GridPane gridpane)
    {
        titleLablel=new Label("title");
        gridpane.add(titleLablel,1,1);
        titletextfield=new TextField();
        gridpane.add(titletextfield,2,1);
        authorlabel=new Label("Author");
        gridpane.add(authorlabel,3,1);
        authortextfield=new TextField();
        gridpane.add(authortextfield,4,1);
        savebutton=new Button("save");
        gridpane.add(savebutton,6,1);
        deletebutton = new Button("delete");
        salebutton=new Button("sale");
        gridpane.add(salebutton,8,1);
        gridpane.add(deletebutton, 7, 1);
    }
    public void addsaveButtonListener(EventHandler<ActionEvent> savebuttonlistener){
        savebutton.setOnAction(savebuttonlistener);
    }
    public void addDeleteButtonListener(EventHandler<ActionEvent> deletebuttonlistener)
    {
        deletebutton.setOnAction(deletebuttonlistener);
    }
    public void addDecrementButtonListener(EventHandler<ActionEvent> salebuttonlistener)
    {
        salebutton.setOnAction(salebuttonlistener);
    }
    public void addDisplayAlertMessage(String title,String header,String content)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public String getTitle(){

        return titletextfield.getText();
    }
    public String getAuthor(){

        return  authortextfield.getText();
    }
    public void addBooktoobservablelist(BookDTO bookdto)
    {

        this.bookobservablelist.add(bookdto);
    }
    public void removebookobservablelist(BookDTO bookdto)
    {

        this.bookobservablelist.remove(bookdto);
    }
    public void decrement_observable_list(BookDTO bookdto)
    {
        for(BookDTO book: bookobservablelist)
        {
            if(book==bookdto)
                book.decrement_number();

        }
    }
    public TableView getBookTableView()
    {
        return bookTableView;
    }
}
