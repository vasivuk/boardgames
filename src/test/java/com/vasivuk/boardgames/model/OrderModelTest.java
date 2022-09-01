package com.vasivuk.boardgames.model;

import com.vasivuk.boardgames.BoardgamesApplication;
import com.vasivuk.boardgames.model.dto.ProductDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
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