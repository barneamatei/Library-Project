package model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate;
    private int number;
    public Long getId() {
        return id;
    }
    public int getNumber(){
        return number;
    }
    public void setNumber(int nr)
    {
        this.number=nr;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }
    public void decrement_number()
    {
        number=number-1;
    }


    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }
}
