package model.builder;

import model.Book;

import java.time.LocalDate;

///Design pattern creational
public class BookBuilder {
    private Book book;
    public BookBuilder()
    {
        this.book=new Book();
    }
    public Book getBook() {

        return book;
    }

    public void setBook(Book book) {

        this.book = book;
    }
    public BookBuilder setId(Long id)
    {
        book.setId(id);
        return this;
    }
    public BookBuilder setTitle(String title)
    {
        book.setTitle(title);
        return this;
    }
    public BookBuilder setAuthor(String author)
    {
        book.setAuthor(author);
        return this;
    }
    public BookBuilder setpulisheddate(LocalDate date)
    {
        book.setPublishedDate(date);
        return this;
    }
    public BookBuilder setNumber(int number)
    {
        book.setNumber(number);
        return this;
    }
    public Book bulid()
    {

        return book;
    }
}
