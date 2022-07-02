package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.repository.OrderItemRepository;
import com.vasivuk.boardgames.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService implements IOrderItemService {

    private final OrderItemRepository repository;

    @Autowired
    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }
}
