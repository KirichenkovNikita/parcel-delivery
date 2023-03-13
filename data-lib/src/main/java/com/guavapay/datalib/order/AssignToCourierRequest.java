package com.guavapay.datalib.order;

import java.io.Serializable;

public class AssignToCourierRequest implements Serializable {
    private Long courierId;
    private Long id;

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
