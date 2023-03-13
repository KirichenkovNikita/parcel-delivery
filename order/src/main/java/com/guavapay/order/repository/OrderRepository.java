package com.guavapay.order.repository;

import com.guavapay.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(Long userId);
    List<Order> findByCourier(Long courierId);
}
