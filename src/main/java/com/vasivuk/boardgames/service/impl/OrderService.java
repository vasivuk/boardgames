package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.repository.OrderItemRepository;
import com.vasivuk.boardgames.repository.OrderRepository;
import com.vasivuk.boardgames.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

}
