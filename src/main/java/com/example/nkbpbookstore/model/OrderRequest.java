package com.example.nkbpbookstore.model;

import java.util.List;

public class OrderRequest {
    private List<String> bookIds;
    private String customerId;

    private Double total;

    // Getters and setters

    public List<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
