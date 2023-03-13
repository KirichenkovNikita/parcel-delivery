package com.guavapay.order.rabbit;

import com.guavapay.datalib.courier.CourierResponse;
import com.guavapay.datalib.courier.CreateCourierRequest;
import com.guavapay.datalib.order.OrderCreateRequest;
import com.guavapay.datalib.order.OrderResponse;
import com.guavapay.order.service.CourierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EnableRabbit
public class CourierListener {
    private static final String QUEUE_CREATE = "courier.create";
    private static final String QUEUE_GET_ALL = "courier.getAll";

    private final CourierService service;

    @Autowired
    public CourierListener(CourierService service) {
        this.service = service;
    }

    @RabbitListener(queues = QUEUE_CREATE)
    public boolean create(CreateCourierRequest message){
        try {
            service.create(message);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    @RabbitListener(queues = QUEUE_GET_ALL)
    public List<CourierResponse> getCourierOrders(){
        try {
            return service.getAll();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ArrayList<>();
        }
    }
}
