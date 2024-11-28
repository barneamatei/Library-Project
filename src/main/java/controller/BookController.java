package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.book.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;

    public BookController(BookView bookView, BookService bookService){
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addSelectionTableListener(new SelectionTableListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSellButtonListener(new SellButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();

            if (title.isEmpty() || author.isEmpty()){
                bookView.displayAlertMessage("Save Error", "Problem at Title or Author fields", "Can not have empty Author or Title fields. Please fill in the fields before submitting Save!");
                bookView.getBooksObservableList().get(0).setTitle("No Name");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setAuthor(author).setTitle(title).setStock(1).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBook) {
                    bookView.displayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    bookView.addBookToObservableList(bookDTO);
                } else {
                    bookView.displayAlertMessage("Save Not Successful", "Book was not added", "There was a problem at adding the book into the database.");
                }
            }
        }
    }

    private class SelectionTableListener implements ChangeListener{

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            BookDTO selectedBookDTO = (BookDTO) newValue;
            System.out.println("Book Author: " + selectedBookDTO.getAuthor() + " Title: " + selectedBookDTO.getTitle());
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean deletionSuccessfull = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deletionSuccessfull){
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "There was a problem in the deletion process. Please restart the application and try again!");
                }
            } else {
                bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "You need to select a row from table before pressing the delete button!");
            }
        }
    }

    private class SellButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();

            if (bookDTO != null) {
                int currentStock = bookDTO.getStock();
                if (currentStock > 0) {
                    bookDTO.setStock(currentStock - 1);
                    boolean updateSuccessful = bookService.sell(BookMapper.convertBookDTOToBook(bookDTO));

                    if (updateSuccessful) {
                        bookView.updateBookInObservableList(bookDTO);
                        bookView.displayAlertMessage(
                                "Sell Successful",
                                "Book Sold",
                                "The book \"" + bookDTO.getTitle() + "\" by " + bookDTO.getAuthor() + " was successfully sold."
                        );
                    } else {
                        bookView.displayAlertMessage(
                                "Sell Not Successful",
                                "Update Error",
                                "There was an error updating the stock. Please try again."
                        );
                    }
                } else {
                    bookView.displayAlertMessage(
                            "Sell Not Successful",
                            "Out of Stock",
                            "The book \"" + bookDTO.getTitle() + "\" is out of stock and cannot be sold."
                    );
                }
            } else {
                bookView.displayAlertMessage(
                        "Sell Not Successful",
                        "No Selection",
                        "You need to select a book from the table before pressing the sell button."
                );
            }
        }
    }


}