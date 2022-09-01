package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.exception.ForbiddenResourceException;
import com.vasivuk.boardgames.exception.InvalidOrderException;
import com.vasivuk.boardgames.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> fetchOrdersByUserId(Long userId);

    Order createOrder(Order order) throws InvalidOrderException;

    Order fetchOrderById(Long userId, Long orderId) throws EntityNotFoundException, ForbiddenResourceException;
}
