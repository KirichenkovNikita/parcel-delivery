package com.guavapay.datalib.order;

import java.io.Serializable;

public class ChangeStatusRequest implements Serializable {
    private OrderStatus status;
    private Long id;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
