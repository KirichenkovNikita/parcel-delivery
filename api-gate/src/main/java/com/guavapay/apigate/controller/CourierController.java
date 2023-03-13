package com.guavapay.apigate.controller;

import com.guavapay.apigate.service.courier.CourierService;
import com.guavapay.datalib.courier.CreateCourierRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api("Controller for working with the courier entity")
@RequestMapping(value = "/api/courier")
public class CourierController {
    private final CourierService courierService;

    @Autowired
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }


    @ApiOperation("Creating a courier. Before creating, it is necessary that a user has already been created, who will be given the role of a courier")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(@Valid @RequestBody CreateCourierRequest request) {
        try {
            courierService.create(request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Get all couriers with their types")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        try {
            return ResponseEntity.ok().body(courierService.getAll());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
