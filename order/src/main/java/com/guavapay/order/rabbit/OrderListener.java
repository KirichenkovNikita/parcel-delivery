package com.guavapay.order.rabbit;

import com.guavapay.datalib.order.AssignToCourierRequest;
import com.guavapay.datalib.order.ChangeDestinationRequest;
import com.guavapay.datalib.order.ChangeStatusRequest;
import com.guavapay.datalib.order.OrderCreateRequest;
import com.guavapay.datalib.order.OrderResponse;
import com.guavapay.datalib.order.SetCoordinatesRequest;
import com.guavapay.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EnableRabbit
public class OrderListener {
    private static final String SUCCESS_MESSAGE = "Request completed successfully";
    private static final String QUEUE_CREATE = "order.create";
    private static final String QUEUE_GET_ORDER_BY_ID = "order.getOrderById";
    private static final String QUEUE_GET_USER_ORDERS = "order.getUserOrders";
    private static final String QUEUE_GET_COURIER_ORDERS = "order.getCourierOrders";
    private static final String QUEUE_CHANGE_STATUS = "order.changeStatus";
    private static final String QUEUE_CHANGE_DESTINATION = "order.changeDestination";
    private static final String QUEUE_CANCEL = "order.cancel";
    private static final String QUEUE_ASSIGN_TO_COURIER = "order.assignToCourier";
    private static final String QUEUE_GET_ALL = "order.getAll";

    private static final String QUEUE_SET_COORDINATES = "order.setCoordinates";
    private final OrderService service;

    public OrderListener(OrderService service) {
        this.service = service;
    }

    @RabbitListener(queues = QUEUE_CREATE)
    public void create(OrderCreateRequest message){
        try {
            service.create(message);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = QUEUE_GET_ORDER_BY_ID)
    public OrderResponse getOrderById(Long id){
        try {
            return service.getOrderById(id);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new OrderResponse();
        }
    }

    @RabbitListener(queues = QUEUE_GET_USER_ORDERS)
    public List<OrderResponse> getUserOrders(Long userId){
        try {
            return service.getUserOrders(userId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ArrayList<>();
        }
    }

    @RabbitListener(queues = QUEUE_GET_COURIER_ORDERS)
    public List<OrderResponse> getCourierOrders(Long courierId){
        try {
            return service.getCourierOrders(courierId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ArrayList<>();
        }
    }

    @RabbitListener(queues = QUEUE_CHANGE_STATUS)
    public String changeStatus(ChangeStatusRequest message){
        try {
            service.changeStatus(message);
            return SUCCESS_MESSAGE;
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpStatus.BAD_REQUEST.getReasonPhrase();
        }
    }

    @RabbitListener(queues = QUEUE_CHANGE_DESTINATION)
    public String changeDestination(ChangeDestinationRequest request){
        try {
            service.changeDestination(request);
            return SUCCESS_MESSAGE;
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpStatus.BAD_REQUEST.getReasonPhrase();
        }
    }

    @RabbitListener(queues = QUEUE_CANCEL)
    public String cancel(Long id) {
        try {
            service.cancel(id);
            return SUCCESS_MESSAGE;
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpStatus.BAD_REQUEST.getReasonPhrase();
        }
    }

    @RabbitListener(queues = QUEUE_ASSIGN_TO_COURIER)
    public void assignToCourier(AssignToCourierRequest request){
        try {
            service.assignToCourier(request);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = QUEUE_GET_ALL)
    public List<OrderResponse> getCourierOrders(){
        try {
            return service.getAll();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ArrayList<>();
        }
    }

    @RabbitListener(queues = QUEUE_SET_COORDINATES)
    public void setCoordinates(SetCoordinatesRequest request){
        try {
            service.setCoordinates(request);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
