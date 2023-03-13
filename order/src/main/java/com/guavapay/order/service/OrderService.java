package com.guavapay.order.service;

import com.guavapay.datalib.order.AssignToCourierRequest;
import com.guavapay.datalib.order.ChangeDestinationRequest;
import com.guavapay.datalib.order.ChangeStatusRequest;
import com.guavapay.datalib.order.OrderCreateRequest;
import com.guavapay.datalib.order.OrderResponse;
import com.guavapay.datalib.order.SetCoordinatesRequest;

import java.util.List;

public interface OrderService {
    void create(OrderCreateRequest request);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getUserOrders(Long userId);

    List<OrderResponse> getCourierOrders(Long courierId);

    List<OrderResponse> getAll();

    void changeStatus(ChangeStatusRequest request);

    void changeDestination(ChangeDestinationRequest request);

    void cancel(Long id);

    void assignToCourier(AssignToCourierRequest request);

    void setCoordinates(SetCoordinatesRequest request);
}
