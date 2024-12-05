package view.BookDTO;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookDTO  {
    private StringProperty author;
    private IntegerProperty number;
    public IntegerProperty numberProperty(){
        if(number==null)
        {
            number= new SimpleIntegerProperty(this,"number");
        }
        return number;
    }
    public int getNumber()
    {
        return numberProperty().get();
    }
    public void setNumber(int number)
    {
        numberProperty().set(number);
    }
    public void decrement_number() {
        // Decrementăm valoarea actuală a atributului 'number' cu 1
        numberProperty().set(numberProperty().get() - 1);
    }

    public StringProperty authorProperty(){
        if(author==null)
        {
            author= new SimpleStringProperty(this,"author");
        }
        return author;
    }

    public void setAuthor(String author) {

        authorProperty().set(author);
    }
    public String getAuthor(){

        return authorProperty().get();
    }
    private StringProperty title;
    public void setTitle(String title) {

        titleProperty().set(title);
    }

    public String getTitle() {

        return titleProperty().get();
    }

    public StringProperty titleProperty() {
        if (title == null) {
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }


}
