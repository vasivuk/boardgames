package com.vasivuk.boardgames.model;

import com.vasivuk.boardgames.BoardgamesApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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

    @Test
    protected void quantityValid() {
        OrderItem quantity = OrderItem.builder().quantity(10).build();

        assertTrue(validator.validateProperty(quantity, "quantity").isEmpty());
    }

    @Test
    protected void quantityInvalid() {
        OrderItem quantityNegative = OrderItem.builder().quantity(-1).build();
        OrderItem quantityZero = OrderItem.builder().quantity(0).build();

        assertFalse(validator.validateProperty(quantityNegative, "quantity").isEmpty());
        assertFalse(validator.validateProperty(quantityZero, "quantity").isEmpty());
    }

    @Test
    protected void productValid() {
        OrderItem product = OrderItem.builder().product(new Product()).build();

        assertTrue(validator.validateProperty(product, "product").isEmpty());
    }

    @Test
    protected void productInvalid() {
        OrderItem product = OrderItem.builder().product(null).build();

        assertFalse(validator.validateProperty(product, "product").isEmpty());
    }

    @Test
    void testToString() {
        OrderItem oi = new OrderItem();
        oi.setProductName("Mars");
        oi.setQuantity(10);

        String s = oi.toString();

        assertTrue(s.contains("Mars"));
        assertTrue(s.contains("10"));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, true",
            "1, 2, false"
    })
    void testEqualsObject(String id1, String id2, boolean equals) {

        OrderItem oi1 = new OrderItem();
        oi1.setId(Long.valueOf(id1));

        OrderItem oi2 = new OrderItem();
        oi2.setId(Long.valueOf(id2));

        assertEquals(equals, oi1.equals(oi2));
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