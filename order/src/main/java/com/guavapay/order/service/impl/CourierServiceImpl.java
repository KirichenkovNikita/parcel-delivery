package com.guavapay.order.service.impl;

import com.guavapay.datalib.courier.CourierResponse;
import com.guavapay.datalib.courier.CreateCourierRequest;
import com.guavapay.datalib.order.OrderResponse;
import com.guavapay.order.model.Courier;
import com.guavapay.order.repository.CourierRepository;
import com.guavapay.order.service.CourierService;
import com.guavapay.order.utils.DataConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourierServiceImpl implements CourierService {
    private final CourierRepository repository;

    @Autowired
    public CourierServiceImpl(CourierRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(CreateCourierRequest request) {
        Courier courier = new Courier();
        courier.setCreated(LocalDateTime.now());
        courier.setUser(request.getUser());
        courier.setStatus(request.getStatus());

        repository.save(courier);
    }

    @Override
    public List<CourierResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(DataConvertor::convertCourier)
                .collect(Collectors.toList());
    }
}
