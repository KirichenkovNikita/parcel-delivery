package com.guavapay.apigate.controller;

import com.guavapay.apigate.service.order.OrderService;
import com.guavapay.datalib.order.AssignToCourierRequest;
import com.guavapay.datalib.order.ChangeDestinationRequest;
import com.guavapay.datalib.order.ChangeStatusRequest;
import com.guavapay.datalib.order.OrderCreateRequest;
import com.guavapay.datalib.order.OrderResponse;
import com.guavapay.datalib.order.SetCoordinatesRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api("Controller for working with the order entity")
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @ApiOperation("Creating a order")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(@Valid @RequestBody OrderCreateRequest request) {
        try {
            orderService.create(request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Get order details by id")
    @RequestMapping(value = "/getOrderById/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(value="id") Long id) {
        try {
            return ResponseEntity.ok().body(orderService.getOrderById(id));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Get all orders created by the user")
    @RequestMapping(value = "/getUserOrders/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable(value="id") Long userId) {
        try {
            return ResponseEntity.ok().body(orderService.getUserOrders(userId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Get all orders assign to courier")
    @RequestMapping(value = "/gerCourierOrders/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<OrderResponse>> gerCourierOrders(@PathVariable(value="id") Long courierId) {
        try {
            return ResponseEntity.ok().body(orderService.gerCourierOrders(courierId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Get all orders")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<OrderResponse>> getAll() {
        try {
            return ResponseEntity.ok().body(orderService.getAll());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Change order status. Cannot be changed if the order is closed or canceled")
    @RequestMapping(value = "/changeStatus", method = RequestMethod.PUT)
    public ResponseEntity changeStatus(@Valid @RequestBody ChangeStatusRequest request) {
        try {
            return ResponseEntity.ok().body(orderService.changeStatus(request));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Change order destination. Cannot be changed if the order is closed or canceled")
    @RequestMapping(value = "/changeDestination", method = RequestMethod.PUT)
    public ResponseEntity changeDestination(@Valid @RequestBody ChangeDestinationRequest request) {
        try {
            return ResponseEntity.ok().body(orderService.changeDestination(request));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Cancel order. Cannot be changed if the order is closed or canceled")
    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.PUT)
    public ResponseEntity cancel(@PathVariable(value="id") Long id) {
        try {
            String result = orderService.cancel(id);
            return ResponseEntity.ok().body(result);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Assign to courier")
    @RequestMapping(value = "/assignToCourier", method = RequestMethod.PUT)
    public ResponseEntity assignToCourier(@Valid @RequestBody AssignToCourierRequest request) {
        try {
            orderService.assignToCourier(request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Get the location of the courier")
    @RequestMapping(value = "/setCoordinates", method = RequestMethod.PUT)
    public ResponseEntity setCoordinates(@Valid @RequestBody SetCoordinatesRequest request) {
        try {
            orderService.setCoordinates(request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
