package com.vasivuk.boardgames.model;

import com.vasivuk.boardgames.BoardgamesApplication;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OrderItem.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = BoardgamesApplication.class)
class OrderItemModelTest extends ModelTest<OrderItem> {

    @Override
    @ParameterizedTest
    @MethodSource("generateValidEntity")
    protected void testWhenValidEntity(OrderItem entity) {
        assertTrue(validator.validateProperty(entity, "quantity").isEmpty());
        assertTrue(validator.validateProperty(entity, "product").isEmpty());
    }

    @Override
    @ParameterizedTest
    @MethodSource("generateInvalidEntity")
    protected void testWhenInvalidEntity(OrderItem entity) {
        assertFalse(validator.validateProperty(entity, "quantity").isEmpty());
        assertFalse(validator.validateProperty(entity, "product").isEmpty());
    }

    private static Stream<Arguments> generateValidEntity() {
        return Stream.of(Arguments.of(OrderItem.builder()
                        .quantity(10)
                        .product(new Product())
                        .build()
                )
        );
    }

    private static Stream<Arguments> generateInvalidEntity() {
        return Stream.of(Arguments.of(OrderItem.builder()
                        .quantity(-1)
                        .product(null)
                        .build()
                )
        );
    }
}