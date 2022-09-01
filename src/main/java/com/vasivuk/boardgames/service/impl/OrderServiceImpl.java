package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.exception.ForbiddenResourceException;
import com.vasivuk.boardgames.exception.InvalidOrderException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.Order;
import com.vasivuk.boardgames.model.OrderItem;
import com.vasivuk.boardgames.repository.OrderRepository;
import com.vasivuk.boardgames.repository.UserRepository;
import com.vasivuk.boardgames.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private UserRepository userRepository;

    @Override
    public List<Order> fetchOrdersByUserId(Long userId) {
        return orderRepository.findOrdersByUserId(userId);
    }

    @Override
    public Order createOrder(Order order) throws InvalidOrderException {

        //Check if user exists in database
        Optional<AppUser> orderUser = userRepository.findByEmail(order.getUser().getEmail());
        if(orderUser.isEmpty()) {
            throw new InvalidOrderException("User that requested an order is not registered.");
        }

        order.setDateSubmitted(new Date());
        BigDecimal total = new BigDecimal(0);
        //Save order without items, so we get order ID to attach on our items
        Order savedOrder = orderRepository.save(order);
        for(OrderItem item: order.getOrderItems()) {
            item.setSubTotal(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
            if(item.getSubTotal().compareTo(new BigDecimal(0)) <= 0) {
                throw new InvalidOrderException("Item price must be more than zero");
            }
            item.setProductName(item.getProduct().getName());
            total = total.add(item.getSubTotal());
            item.setOrder(savedOrder);
        }
        if(total.compareTo(new BigDecimal(0)) <= 0) {
            throw new InvalidOrderException("Total price must be more than zero");
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
        if ( !userThatRequestedOrder.getUserRole().equals("ADMIN") && !Objects.equals(order.getUser().getId(), userId)) {
            throw new ForbiddenResourceException("The order is not made by user that requested it.");
        }
        return order;
    }
}
