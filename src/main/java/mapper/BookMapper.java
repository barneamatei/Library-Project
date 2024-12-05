package mapper;

import model.Book;
import model.builder.BookBuilder;
import view.BookDTO.BookDTO;
import view.builder.BookDTOBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDTO convertBooktoBookDTO(Book book)
    {
        return new BookDTOBuilder().setTitle(book.getTitle()).setAuthor(book.getAuthor()).setNumber(book.getNumber()).build();
    }
    public static Book convertbookDTOtoBOOk(BookDTO bookdto)
    {
        return new BookBuilder().setTitle(bookdto.getTitle()).setAuthor(bookdto.getAuthor()).setNumber(bookdto.getNumber()).setpulisheddate(LocalDate.of(2010,1,1)).bulid();
    }
    public static List<BookDTO> convertBookLIsttoBookDTOlIST(List<Book> books)
    {
        if (books == null) {
           return  new ArrayList<>();
        }
        return books.parallelStream().map(BookMapper::convertBooktoBookDTO).collect(Collectors.toList());
    }
    public static List<Book> convertbookdtotobooklist(List <BookDTO> bookdto)
    {
        return bookdto.parallelStream().map(BookMapper::convertbookDTOtoBOOk).collect(Collectors.toList());
    }
}
