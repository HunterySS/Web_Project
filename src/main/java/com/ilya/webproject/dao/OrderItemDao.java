package com.ilya.webproject.dao;

import com.ilya.webproject.model.OrderItem;
import java.util.List;

public interface OrderItemDao {
    List<OrderItem> findByOrderId(Long orderId);
}