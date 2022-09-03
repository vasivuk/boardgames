package com.vasivuk.boardgames.model;

import com.vasivuk.boardgames.BoardgamesApplication;
import com.vasivuk.boardgames.model.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Order.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = BoardgamesApplication.class)
class OrderModelTest extends ModelTest<Order> {


    @Override
    @ParameterizedTest
    @MethodSource("generateValidEntity")
    protected void testWhenValidEntity(Order entity) {
        assertTrue(validator.validateProperty(entity, "user").isEmpty());
        assertTrue(validator.validateProperty(entity, "orderItems").isEmpty());
    }

    @Override
    @ParameterizedTest
    @MethodSource("generateInvalidEntity")
    protected void testWhenInvalidEntity(Order entity) {
        assertFalse(validator.validateProperty(entity, "user").isEmpty());
        assertFalse(validator.validateProperty(entity, "orderItems").isEmpty());
    }

    @Test
    protected void testUserValid() {
        Order user = Order.builder().user(new AppUser()).build();

        assertTrue(validator.validateProperty(user, "user").isEmpty());
    }

    @Test
    protected void testUserInvalid() {
        Order user = Order.builder().user(null).build();

        assertFalse(validator.validateProperty(user, "user").isEmpty());
    }

    @Test
    protected void testOrderItemsValid() {
        Order orderItems = Order.builder().orderItems(List.of(new OrderItem())).build();

        assertTrue(validator.validateProperty(orderItems, "orderItems").isEmpty());
    }

    @Test
    protected void testOrderItemsInvalid() {
        Order orderItemsNull = Order.builder().orderItems(null).build();
        Order orderItemsEmpty = Order.builder().orderItems(new ArrayList<>()).build();

        assertFalse(validator.validateProperty(orderItemsNull, "orderItems").isEmpty());
        assertFalse(validator.validateProperty(orderItemsEmpty, "orderItems").isEmpty());
    }
    @ParameterizedTest
    @CsvSource({
            "1, 1, true",
            "1, 2, false"
    })
    void testEqualsObject(String id1, String id2, boolean equals) {

        Order o1 = new Order();
        o1.setId(Long.valueOf(id1));

        Order o2 = new Order();
        o2.setId(Long.valueOf(id2));

        assertEquals(equals, o1.equals(o2));
    }


    private static Stream<Arguments> generateValidEntity() {
        return Stream.of(
                Arguments.of(Order.builder()
                        .user(new AppUser())
                        .orderItems(List.of(new OrderItem()))
                        .build()
                )
        );
    }

    private static Stream<Arguments> generateInvalidEntity() {
        return Stream.of(
                Arguments.of(Order.builder()
                        .user(null)
                        .orderItems(new ArrayList<>())
                        .build()
                ),
                Arguments.of(Order.builder()
                        .user(null)
                        .orderItems(null)
                        .build()
                )

        );
    }
}