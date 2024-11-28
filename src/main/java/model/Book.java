package model;
import java.time.LocalDate;
import java.util.Date;

// POJO - Plain Old Java Object
//Java Bean
public class Book{
    private Long id;
    private String author;
    private String title;
    private int stock;
    private LocalDate publishedDate;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

    @Override
    public String toString(){
        return "Book: ID: " + id + " Title: " + title + " Author: " + author +" Stock : " + stock + " Published Date: " + publishedDate;

    }
}