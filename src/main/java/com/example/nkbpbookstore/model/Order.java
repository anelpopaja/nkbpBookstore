package com.example.nkbpbookstore.model;

import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node("Order")
public class Order {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    private String customerId;

    @Property("total")
    private Double total;

    @Relationship(type = "CONTAINS")
    private List<Book> books = new ArrayList<>();


    public Order(){}

    public Order(Double total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }



    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", total=" + total +
                ", books=" + books +
                '}';
    }
}
