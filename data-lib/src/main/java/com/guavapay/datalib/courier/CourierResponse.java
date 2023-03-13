package com.guavapay.datalib.courier;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CourierResponse  implements Serializable {
    private Long id;
    private Long user_id;
    private CourierType type;
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public CourierType getType() {
        return type;
    }

    public void setType(CourierType type) {
        this.type = type;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
