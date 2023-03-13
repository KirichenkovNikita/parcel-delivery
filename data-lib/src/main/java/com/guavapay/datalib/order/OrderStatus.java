package com.guavapay.datalib.order;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    NEW,
    IN_PROGRESS,
    CLOSED,
    CANCEL
}
