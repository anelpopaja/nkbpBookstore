package com.example.nkbpbookstore.model;

import java.time.LocalDate;
import java.util.List;

public class BookRequest {
    private String title;
    private String isbn;
    private LocalDate publishingDate;
    private Double price;
    private String description;
    private List<String> genres;
    private List<Author> authors;

    // getters and setters


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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthor(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "BookRequest{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publishingDate=" + publishingDate +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", genres=" + genres +
                ", authors=" + authors +
                '}';
    }
}
