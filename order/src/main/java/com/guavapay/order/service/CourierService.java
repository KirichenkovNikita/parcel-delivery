package com.guavapay.order.service;

import com.guavapay.datalib.courier.CourierResponse;
import com.guavapay.datalib.courier.CreateCourierRequest;
import com.guavapay.datalib.order.OrderResponse;

import java.util.List;

public interface CourierService {

    void create(CreateCourierRequest request);
    List<CourierResponse> getAll();
}
