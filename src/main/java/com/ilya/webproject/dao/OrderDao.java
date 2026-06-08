package com.ilya.webproject.dao;

import com.ilya.webproject.model.Order;
import com.ilya.webproject.model.OrderItem;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order save(Order order, List<OrderItem> items);
    Optional<Order> findById(Long id);
    List<Order> findByUserId(Long userId);
    List<OrderItem> findItemsByOrderId(Long orderId);
    boolean updateStatus(Long orderId, String status);
}