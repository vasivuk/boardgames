package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.model.Order;
import com.vasivuk.boardgames.repository.OrderRepository;
import com.vasivuk.boardgames.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Override
    public List<Order> fetchOrdersByUserId(Long userId) {
        return orderRepository.findOrdersByUserId(userId);
    }

    @Override
    public Order createOrder(Long userId, Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order fetchOrderById(Long userId, Long orderId) {
        return null;
    }
}
