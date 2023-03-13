package com.guavapay.datalib.order;

import java.io.Serializable;

public class SetCoordinatesRequest implements Serializable {
    private long orderId;
    private String coordinates;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
