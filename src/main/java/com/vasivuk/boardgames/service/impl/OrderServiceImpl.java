package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.repository.OrderItemRepository;
import com.vasivuk.boardgames.repository.OrderRepository;
import com.vasivuk.boardgames.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

}
