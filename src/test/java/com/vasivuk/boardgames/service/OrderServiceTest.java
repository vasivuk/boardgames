package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.exception.ForbiddenResourceException;
import com.vasivuk.boardgames.exception.InvalidOrderException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.Order;
import com.vasivuk.boardgames.model.OrderItem;
import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.repository.OrderRepository;
import com.vasivuk.boardgames.repository.ProductRepository;
import com.vasivuk.boardgames.repository.UserRepository;
import com.vasivuk.boardgames.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = OrderService.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    private OrderService orderService;
    private Order order;
    private AppUser user;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository, userRepository, productRepository);
        OrderItem item1;
        OrderItem item2;
        user = AppUser.builder().id(1L).email("marko@gmail.com").userRole("USER").build();
        item1 = OrderItem.builder()
                .product(Product.builder()
                        .name("Product 1")
                        .price(new BigDecimal(100))
                        .build())
                .quantity(2)
                .build();
        item2 = OrderItem.builder()
                .product(Product.builder()
                        .name("Product 2")
                        .price(new BigDecimal(200))
                        .build())
                .quantity(1)
                .build();
        order = Order.builder()
                .user(user)
                .orderItems(List.of(item1, item2))
                .build();
    }

    @Test
    void Create_order_valid() throws InvalidOrderException {
        //given
        given(orderRepository.save(order)).willReturn(order);
        given(userRepository.findByEmail(order.getUser().getEmail())).willReturn(Optional.of(user));

        //when
        Order savedOrder = orderService.createOrder(order);

        //then
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getTotalPrice()).isEqualTo(new BigDecimal(400));
    }

    @Test
    void Create_order_invalid_user() {
        //given
        given(userRepository.findByEmail(order.getUser().getEmail())).willReturn(Optional.empty());

        //when
        //then
        assertThrows(InvalidOrderException.class, () -> orderService.createOrder(order));
    }

    @Test
    void Create_order_invalid_subtotal() {
        //given
        given(orderRepository.save(order)).willReturn(order);
        given(userRepository.findByEmail(order.getUser().getEmail())).willReturn(Optional.of(user));
        order.setOrderItems(List.of(OrderItem.builder()
                .product(Product.builder()
                        .name("Product 3")
                        .price(new BigDecimal(-300))
                        .build())
                .quantity(4)
                .build()));
        //when
        //then
        assertThrows(InvalidOrderException.class, () -> orderService.createOrder(order));
    }

    @Test
    void Can_fetch_orders_by_user_id() {
        Long id = 1L;
        orderService.fetchOrdersByUserId(id);

        verify(orderRepository).findOrdersByUserId(id);
    }

    @Test
    void Fetch_order_does_not_exist() {
        Long id = 1L;
        given(orderRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.fetchOrderById(1L, id));

    }

    @Test
    void Fetch_order_invalid_user() {
        Long id = 1L;
        Long userId = 2L;
        AppUser anotherUser = AppUser.builder()
                .id(2L)
                .userRole("USER")
                .build();
        given(orderRepository.findById(id)).willReturn(Optional.of(order));
        given(userRepository.getById(userId)).willReturn(anotherUser);

        assertThrows(ForbiddenResourceException.class, () -> orderService.fetchOrderById(userId, id));
    }

    @Test
    void Fetch_order_administrator_request() throws ForbiddenResourceException, EntityNotFoundException {
        Long id = 1L;
        Long userId = 2L;
        AppUser adminUser = AppUser.builder()
                .id(2L)
                .userRole("ADMIN")
                .build();
        given(orderRepository.findById(id)).willReturn(Optional.of(order));
        given(userRepository.getById(userId)).willReturn(adminUser);

        Order requestedOrder = orderService.fetchOrderById(userId, id);

        assertThat(requestedOrder).isNotNull();
    }

    @Test
    void Fetch_order_valid_user() throws ForbiddenResourceException, EntityNotFoundException {
        Long id = 1L;
        Long userId = 1L;

        given(orderRepository.findById(id)).willReturn(Optional.of(order));
        given(userRepository.getById(userId)).willReturn(order.getUser());

        Order requestedOrder = orderService.fetchOrderById(userId, id);

        assertThat(requestedOrder).isNotNull();
    }
}