package com.guavapay.datalib.order;

import java.io.Serializable;

public class OrderCreateRequest implements Serializable {
    private Long user;
    private String destination;
    private String details;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
