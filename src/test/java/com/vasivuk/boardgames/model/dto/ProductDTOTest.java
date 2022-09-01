package com.vasivuk.boardgames.model.dto;

import com.vasivuk.boardgames.BoardgamesApplication;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.model.ModelTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProductDTO.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = BoardgamesApplication.class)
class ProductDTOTest extends ModelTest<ProductDTO> {
    @Override
    @ParameterizedTest
    @MethodSource("generateValidEntity")
    protected void testWhenValidEntity(ProductDTO entity) {
        assertTrue(validator.validateProperty(entity, "name").isEmpty());
        assertTrue(validator.validateProperty(entity, "description").isEmpty());
        assertTrue(validator.validateProperty(entity, "gameTime").isEmpty());
        assertTrue(validator.validateProperty(entity, "complexity").isEmpty());
        assertTrue(validator.validateProperty(entity, "rating").isEmpty());
        assertTrue(validator.validateProperty(entity, "stockQuantity").isEmpty());
    }

    @Override
    @ParameterizedTest
    @MethodSource("generateInvalidEntity")
    protected void testWhenInvalidEntity(ProductDTO entity) {
        assertFalse(validator.validateProperty(entity, "name").isEmpty());
        assertFalse(validator.validateProperty(entity, "description").isEmpty());
        assertFalse(validator.validateProperty(entity, "gameTime").isEmpty());
        assertFalse(validator.validateProperty(entity, "complexity").isEmpty());
        assertFalse(validator.validateProperty(entity, "rating").isEmpty());
        assertFalse(validator.validateProperty(entity, "stockQuantity").isEmpty());
    }

    @Test
    protected void complexityValid() {
        ProductDTO complexityValid = ProductDTO.builder().complexity(1.25).build();

        assertTrue(validator.validateProperty(complexityValid, "complexity").isEmpty());
    }

    @Test
    protected void complexityInvalid() {
        ProductDTO lessThanZero = ProductDTO.builder().complexity(-0.1).build();
        ProductDTO moreThanFive = ProductDTO.builder().complexity(-0.1).build();

        assertFalse(validator.validateProperty(lessThanZero, "complexity").isEmpty());
        assertFalse(validator.validateProperty(moreThanFive, "complexity").isEmpty());
    }

    private static Stream<Arguments> generateValidEntity() {
        return Stream.of(
                Arguments.of(ProductDTO.builder()
                        .name("Product")
                        .price(new BigDecimal(100))
                        .description("Desc")
                        .gameTime(100)
                        .complexity(3.0)
                        .rating(3.0)
                        .stockQuantity(10)
                        .build())
        );
    }

    private static Stream<Arguments> generateInvalidEntity() {
        String invalidDescription = " ".repeat(1001);
        return Stream.of(
                Arguments.of(ProductDTO.builder()
                        .name(null)
                        .price(new BigDecimal(-10))
                        .description(invalidDescription)
                        .gameTime(-10)
                        .complexity(6)
                        .rating(6)
                        .stockQuantity(-10)
                        .build()),
                Arguments.of(ProductDTO.builder()
                        .name("")
                        .price(new BigDecimal(-10))
                        .description(invalidDescription)
                        .gameTime(-10)
                        .complexity(-1)
                        .rating(-1)
                        .stockQuantity(-10)
                        .build())
        );
    }
}