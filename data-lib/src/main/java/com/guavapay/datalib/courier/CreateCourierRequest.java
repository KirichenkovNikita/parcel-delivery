package com.guavapay.datalib.courier;

import java.io.Serializable;

public class CreateCourierRequest implements Serializable {
    private Long user;
    private Long creator;
    private CourierType status;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public CourierType getStatus() {
        return status;
    }

    public void setStatus(CourierType status) {
        this.status = status;
    }
}
