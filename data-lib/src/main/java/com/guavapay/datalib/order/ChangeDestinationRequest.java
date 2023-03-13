package com.guavapay.datalib.order;

import java.io.Serializable;

public class ChangeDestinationRequest implements Serializable {
    private String destination;
    private Long id;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
