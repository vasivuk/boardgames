package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.exception.ForbiddenResourceException;
import com.vasivuk.boardgames.model.Order;
import com.vasivuk.boardgames.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.vasivuk.boardgames.configuration.Routes.ID;
import static com.vasivuk.boardgames.configuration.Routes.USER_COMMON;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:3000/**", allowCredentials = "true")
public class OrderController {

    private final OrderService orderService;

    @GetMapping(USER_COMMON + ID + "/orders")
    public List<Order> fetchOrdersByUserId(@PathVariable("id") Long userId) {
        return orderService.fetchOrdersByUserId(userId);
    }

    @GetMapping(USER_COMMON + ID + "/orders" + "/{orderId}")
    public Order fetchOrderById(@PathVariable("id") Long userId, @PathVariable("orderId") Long orderId) throws ForbiddenResourceException, EntityNotFoundException {
        return orderService.fetchOrderById(userId, orderId);
    }

    @PostMapping("/api/orders")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
}
