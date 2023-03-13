package com.guavapay.order.utils;

import com.guavapay.datalib.courier.CourierResponse;
import com.guavapay.datalib.courier.CourierType;
import com.guavapay.datalib.order.OrderResponse;
import com.guavapay.order.model.Courier;
import com.guavapay.order.model.Order;

import java.time.LocalDateTime;

public class DataConvertor {
    public static OrderResponse convertOrder(Order order) {
        OrderResponse result = new OrderResponse();
        result.setClosed(order.getClosed());
        result.setCoordinate(order.getCoordinate());
        result.setCourier(order.getCourier());
        result.setCreated(order.getCreated());
        result.setDetails(order.getDetails());
        result.setId(order.getId());
        result.setStatus(order.getStatus().toString());
        result.setUser(order.getUser());
        result.setDestination(order.getDestination());
        return result;
    }

    public static CourierResponse convertCourier(Courier courier) {
        CourierResponse response = new CourierResponse();
        response.setId(courier.getId());
        response.setType(courier.getStatus());
        response.setCreated(courier.getCreated());
        response.setUser_id(courier.getUser());

        return response;
    }
}
