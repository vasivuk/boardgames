package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.configuration.Routes;
import com.vasivuk.boardgames.model.Order;
import com.vasivuk.boardgames.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.vasivuk.boardgames.configuration.Routes.ID;
import static com.vasivuk.boardgames.configuration.Routes.USER_COMMON;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(USER_COMMON + ID + "/orders")
    public List<Order> fetchOrdersByUserId(@PathVariable("id") Long userId) {
        return orderService.fetchOrdersByUserId(userId);
    }

    @GetMapping(USER_COMMON + ID + "/orders" + "/{orderId}")
    public Order fetchOrderById(@PathVariable("id") Long userId, @PathVariable("orderId") Long orderId) {
        return orderService.fetchOrderById(userId, orderId);
    }

    @PostMapping(USER_COMMON + ID + "/orders")
    public Order createOrder(@PathVariable("id") Long userId, @RequestBody Order order) {
        return orderService.createOrder(userId, order);
    }
}
