package com.guavapay.apigate.service.order.impl;

import com.guavapay.apigate.repository.UserRepository;
import com.guavapay.apigate.service.order.OrderService;
import com.guavapay.apigate.utils.UserValidator;
import com.guavapay.datalib.order.AssignToCourierRequest;
import com.guavapay.datalib.order.ChangeDestinationRequest;
import com.guavapay.datalib.order.ChangeStatusRequest;
import com.guavapay.datalib.order.OrderCreateRequest;
import com.guavapay.datalib.order.OrderResponse;
import com.guavapay.datalib.order.SetCoordinatesRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String NULL_DESTINATION_MESSAGE = "When creating an order, the destination cannot be null";
    private static final String ID_IS_NULL = "Id cannot be null";
    private static final String COURIER_ID_IS_NULL = "Courier Id cannot be null";

    private final AmqpTemplate amqpTemplate;
    private final UserValidator userValidator;
    @Autowired
    public OrderServiceImpl(AmqpTemplate amqpTemplate, UserValidator userValidator) {
        this.amqpTemplate = amqpTemplate;
        this.userValidator = userValidator;
    }


    @Override
    public void create(OrderCreateRequest request) {
        validateCreate(request);
        amqpTemplate.convertAndSend("order.create", request);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return (OrderResponse)amqpTemplate.convertSendAndReceive("order.getOrderById", id);
    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        return (List<OrderResponse>)amqpTemplate.convertSendAndReceive("order.getUserOrders", userId);
    }

    @Override
    public List<OrderResponse> gerCourierOrders(Long courierId) {
        return (List<OrderResponse>)amqpTemplate.convertSendAndReceive("order.gerCourierOrders", courierId);
    }

    @Override
    public List<OrderResponse> getAll() {
        return (List<OrderResponse>)amqpTemplate.convertSendAndReceive("order.getAll", 0);
    }

    @Override
    public String changeStatus(ChangeStatusRequest request) {
        if (request.getId() == null) {
            throw new IllegalArgumentException(ID_IS_NULL);
        }

        return Objects.requireNonNull(amqpTemplate.convertSendAndReceive("order.changeStatus", request)).toString();
    }

    @Override
    public String changeDestination(ChangeDestinationRequest request) {
        if (request.getId() == null) {
            throw new IllegalArgumentException(ID_IS_NULL);
        }

        return Objects.requireNonNull(amqpTemplate.convertSendAndReceive("order.changeDestination", request)).toString();
    }

    @Override
    public String cancel(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(ID_IS_NULL);
        }

        return Objects.requireNonNull(amqpTemplate.convertSendAndReceive("order.cancel", id)).toString();
    }

    @Override
    public void assignToCourier(AssignToCourierRequest request) {
        if (request.getId() == null) {
            throw new IllegalArgumentException(ID_IS_NULL);
        }

        if (request.getCourierId() == null) {
            throw new IllegalArgumentException(COURIER_ID_IS_NULL);
        }

        amqpTemplate.convertAndSend("order.assignToCourier", request);
    }

    @Override
    public void setCoordinates(SetCoordinatesRequest request) {
        amqpTemplate.convertAndSend("order.setCoordinates", request);
    }

    private void validateCreate(OrderCreateRequest request) {
        Long user = request.getUser();
        userValidator.checkUser(user);

        if (request.getDestination() == null || request.getDestination().isEmpty()) {
            throw new IllegalArgumentException(NULL_DESTINATION_MESSAGE);
        }
    }
}
