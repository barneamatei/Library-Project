package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.book.BookService;
import view.BookDTO.BookDTO;
import view.BookView;
import view.builder.BookDTOBuilder;

public class BookControler {
    private final BookView bookview;
    private final BookService bookservice;

    public BookControler(BookView bookview, BookService bookService) {
        this.bookview = bookview;
        this.bookservice = bookService;
        this.bookview.addsaveButtonListener(new SaveButtonListener());
        this.bookview.addDeleteButtonListener(new DeleteButtonListener());
        this.bookview.addDecrementButtonListener(new saleButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String title = bookview.getTitle();
            String author = bookview.getAuthor();
            if (title.isEmpty() || author.isEmpty()) {
                bookview.addDisplayAlertMessage("error", "error", "error");
            } else {
                BookDTO bookdto = new BookDTOBuilder().setTitle(title).setAuthor(author).build();
                boolean savedbook = bookservice.save(BookMapper.convertbookDTOtoBOOk(bookdto));
                if (savedbook) {
                    bookview.addDisplayAlertMessage("book", "added", "success");
                    bookview.addBooktoobservablelist(bookdto);
                }
            }
        }
    }
    private class saleButtonListener implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookdto=(BookDTO) bookview.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookdto!=null)
            {
                boolean decrementsucces=bookservice.decrement(BookMapper.convertbookDTOtoBOOk(bookdto));
                if(decrementsucces)
                {
                    bookservice.add_sale();
                    bookview.addDisplayAlertMessage("delete","succes","delete");
                    bookview.decrement_observable_list(bookdto);
                }
            }
        }
    }
    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookDTO bookdto=(BookDTO) bookview.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookdto!=null)
            {
                boolean deletesuces=bookservice.delete(BookMapper.convertbookDTOtoBOOk(bookdto));
                if(deletesuces)
                {
                    bookview.addDisplayAlertMessage("delete","succes","delete");
                    bookview.removebookobservablelist(bookdto);
                }
                else
                    bookview.addDisplayAlertMessage("delete","fial","delete");
            }
            else{
                bookview.addDisplayAlertMessage("delete","error","delete");
            }
        }
    }

}
