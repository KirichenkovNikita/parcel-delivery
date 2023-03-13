package com.guavapay.apigate.service.courier;

import com.guavapay.datalib.courier.CourierResponse;
import com.guavapay.datalib.courier.CreateCourierRequest;

import java.util.List;

public interface CourierService {
    void create(CreateCourierRequest request);

    List<CourierResponse> getAll();
}
