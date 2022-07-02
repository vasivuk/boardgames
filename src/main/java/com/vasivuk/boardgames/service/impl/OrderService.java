package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.repository.OrderRepository;
import com.vasivuk.boardgames.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }
}
