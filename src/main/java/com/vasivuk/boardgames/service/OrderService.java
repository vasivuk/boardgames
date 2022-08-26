package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> fetchOrdersByUserId(Long userId);

    Order createOrder(Long userId, Order order);

    Order fetchOrderById(Long userId, Long orderId);
}
