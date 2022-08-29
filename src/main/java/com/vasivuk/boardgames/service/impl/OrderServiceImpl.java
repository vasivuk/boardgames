package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.exception.ForbiddenResourceException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.Order;
import com.vasivuk.boardgames.model.OrderItem;
import com.vasivuk.boardgames.repository.OrderRepository;
import com.vasivuk.boardgames.repository.UserRepository;
import com.vasivuk.boardgames.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Order> fetchOrdersByUserId(Long userId) {
        return orderRepository.findOrdersByUserId(userId);
    }

    @Override
    public Order createOrder(Order order) {

        if(order.getUser() == null) {
            log.info("User not present");
        }
        BigDecimal total = new BigDecimal(0);
        order.setDateSubmitted(new Date());
        Order savedOrder = orderRepository.save(order);
        for(OrderItem item: order.getOrderItems()) {
            item.setSubTotal(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
            item.setProductName(item.getProduct().getName());
            total = total.add(item.getSubTotal());
            item.setOrder(savedOrder);
        }
        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    @Override
    public Order fetchOrderById(Long userId, Long orderId) throws EntityNotFoundException, ForbiddenResourceException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new EntityNotFoundException("Order doesn't exist");
        }
        Order order = optionalOrder.get();
        AppUser userThatRequestedOrder = userRepository.getById(userId);

        // If the user is not ADMIN and userId on the order is different from user fetching it...
        if (!userThatRequestedOrder.getUserRole().equals("ADMIN") && order.getUser().getId() != userId) {
            throw new ForbiddenResourceException("The order is not made by user that requested it.");
        }
        return order;
    }
}
