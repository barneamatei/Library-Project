package view.builder;

import view.BookDTO.BookDTO;

public class BookDTOBuilder {
    private BookDTO bookDTO;

    public BookDTOBuilder() {

        bookDTO = new BookDTO();
    }
    public BookDTOBuilder setNumber(int number)
    {
        bookDTO.setNumber(number);
        return this;
    }

    public BookDTOBuilder setAuthor(String author) {
        bookDTO.setAuthor(author);
        return this;
    }

    public BookDTOBuilder setTitle(String title) {
        bookDTO.setTitle(title);
        return this;
    }

    public BookDTO build() {

        return bookDTO;
    }
}
