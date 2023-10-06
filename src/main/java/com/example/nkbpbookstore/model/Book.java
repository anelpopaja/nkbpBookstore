package com.example.nkbpbookstore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Node("Book")
public class Book {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    @Property("title")
    private String title;

    @Property("isbn")
    private String isbn;

    @Property("publishingDate")
    private LocalDate publishingDate;

    @Property("price")
    private Double price;

    @Property("description")
    private String description;



    @Relationship(type = "WRITTEN_BY")
    private List<Author> authors = new ArrayList<>();

    @Relationship(type = "BELONGS_TO")
    private List<Genre> genres = new ArrayList<>();


    public Book(){}

    public Book(String title, String isbn, LocalDate publishingDate, Double price, String description) {
        this.title = title;
        this.isbn = isbn;
        this.publishingDate = publishingDate;
        this.price = price;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }




    @Override
    public String toString() {
        return "\nBook{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publishingDate=" + publishingDate +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", genres=" + genres +
                ", authors=" + authors +
                '}';
    }
}