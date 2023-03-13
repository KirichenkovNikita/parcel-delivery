package com.guavapay.order.service.impl;

import com.guavapay.datalib.order.AssignToCourierRequest;
import com.guavapay.datalib.order.ChangeDestinationRequest;
import com.guavapay.datalib.order.ChangeStatusRequest;
import com.guavapay.datalib.order.OrderCreateRequest;
import com.guavapay.datalib.order.OrderResponse;
import com.guavapay.datalib.order.OrderStatus;
import com.guavapay.datalib.order.SetCoordinatesRequest;
import com.guavapay.order.model.Order;
import com.guavapay.order.repository.OrderRepository;
import com.guavapay.order.service.OrderService;
import com.guavapay.order.utils.DataConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private static final String ORDER_IS_CLOSED_MESSAGE = "Order closed or canceled";
    private static final String ORDER_IN_PROGRESS_MESSAGE = "The order has already been processed and cannot be canceled";
    private static final String ASSIGN_TO_COURIER_MESSAGE = "Courier already assigned";
    private final OrderRepository repository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(OrderCreateRequest request) {
        Order order = new Order();
        order.setDestination(request.getDestination());
        order.setUser(request.getUser());
        order.setStatus(OrderStatus.NEW);
        order.setCreated(LocalDateTime.now());
        order.setDetails(request.getDetails());
        repository.save(order);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Optional<Order> order = repository.findById(id);
        return order.map(DataConvertor::convertOrder).orElseGet(OrderResponse::new);

    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        return repository.findByUser(userId)
                .stream()
                .map(DataConvertor::convertOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getCourierOrders(Long courierId) {
        return repository.findByCourier(courierId)
                .stream()
                .map(DataConvertor::convertOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(DataConvertor::convertOrder)
                .collect(Collectors.toList());
    }

    @Override
    public void changeStatus(ChangeStatusRequest request) {
        Optional<Order> orderOptional = repository.findById(request.getId());
        if (orderOptional.isEmpty()) {
            return;
        }

        Order order = orderOptional.get();
        OrderStatus status = request.getStatus();
        validateStatusIfClosed(order);

        order.setStatus(status);
        repository.save(order);
    }

    @Override
    public void changeDestination(ChangeDestinationRequest request) {
        Optional<Order> orderOptional = repository.findById(request.getId());
        if (orderOptional.isEmpty()) {
            return;
        }

        Order order = orderOptional.get();
        validateStatusIfClosed(order);

        order.setDestination(request.getDestination());
        repository.save(order);
    }

    @Override
    public void cancel(Long id) {
        Optional<Order> orderOptional = repository.findById(id);
        if (orderOptional.isEmpty()) {
            return;
        }

        Order order = orderOptional.get();
        validateStatusIfClosed(order);

        order.setStatus(OrderStatus.CANCEL);
        order.setClosed(LocalDateTime.now());
        repository.save(order);
    }

    @Override
    public void assignToCourier(AssignToCourierRequest request) {
        Optional<Order> orderOptional = repository.findById(request.getId());
        if (orderOptional.isEmpty()) {
            return;
        }

        Order order = orderOptional.get();
        validateAssignToCourier(order);

        order.setCourier(request.getCourierId());
        repository.save(order);
    }

    @Override
    public void setCoordinates(SetCoordinatesRequest request) {
        Optional<Order> orderOptional = repository.findById(request.getOrderId());
        if (orderOptional.isEmpty()) {
            return;
        }

        Order order = orderOptional.get();
        order.setCoordinate(request.getCoordinates());
        repository.save(order);
    }

    private void validateStatusIfClosed(Order order) {
        OrderStatus status = order.getStatus();

        if (status == OrderStatus.CLOSED || status == OrderStatus.CANCEL) {
            throw new IllegalArgumentException(ORDER_IS_CLOSED_MESSAGE);
        }

        if (status == OrderStatus.IN_PROGRESS) {
            throw new IllegalArgumentException(ORDER_IN_PROGRESS_MESSAGE);
        }
    }

    private void validateAssignToCourier(Order order) {
        if (order.getCourier() != null) {
            throw new IllegalArgumentException(ASSIGN_TO_COURIER_MESSAGE);
        }
    }
}
