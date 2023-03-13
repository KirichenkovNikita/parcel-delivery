package com.guavapay.apigate.service.order;

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

    List<OrderResponse> gerCourierOrders(Long courierId);

    List<OrderResponse> getAll();


    String changeStatus(ChangeStatusRequest request);

    String changeDestination(ChangeDestinationRequest request);

    String cancel(Long id);

    void assignToCourier(AssignToCourierRequest request);

    void setCoordinates(SetCoordinatesRequest request);
}
