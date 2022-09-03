package com.vasivuk.boardgames.model.dto;

import com.vasivuk.boardgames.BoardgamesApplication;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.model.ModelTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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
    protected void nameValid() {
        ProductDTO name = ProductDTO.builder().name("name").build();

        assertTrue(validator.validateProperty(name, "name").isEmpty());
    }

    @Test
    protected void nameInvalid() {
        ProductDTO nameNull = ProductDTO.builder().name(null).build();
        ProductDTO nameBlank = ProductDTO.builder().name("   ").build();
        ProductDTO nameMoreThan40 = ProductDTO.builder().name("x".repeat(41)).build();

        assertFalse(validator.validateProperty(nameNull, "name").isEmpty());
        assertFalse(validator.validateProperty(nameBlank, "name").isEmpty());
        assertFalse(validator.validateProperty(nameMoreThan40, "name").isEmpty());
    }

    @Test
    protected void descriptionValid() {
        ProductDTO description = ProductDTO.builder().description("Some description").build();
        ProductDTO descriptionBlank = ProductDTO.builder().description("   ").build();

        assertTrue(validator.validateProperty(description, "description").isEmpty());
        assertTrue(validator.validateProperty(descriptionBlank, "description").isEmpty());
    }

    @Test
    protected void descriptionInvalid() {
        ProductDTO descriptionOver1000 = ProductDTO.builder().description(" ".repeat(1001)).build();
        assertFalse(validator.validateProperty(descriptionOver1000, "name").isEmpty());
    }

    @Test
    protected void gameTimeValid() {
        ProductDTO gameTime = ProductDTO.builder().gameTime(10).build();

        assertTrue(validator.validateProperty(gameTime, "gameTime").isEmpty());
    }

    @Test
    protected void gameTimeInvalid() {
        ProductDTO gameTimeNegative = ProductDTO.builder().gameTime(-10).build();
        ProductDTO gameTimeZero = ProductDTO.builder().gameTime(-10).build();

        assertFalse(validator.validateProperty(gameTimeZero, "gameTime").isEmpty());
        assertFalse(validator.validateProperty(gameTimeNegative, "gameTime").isEmpty());
    }

    @Test
    protected void ratingValid() {
        ProductDTO rating = ProductDTO.builder().rating(3.25).build();
        ProductDTO ratingZero = ProductDTO.builder().rating(0).build();
        ProductDTO ratingFive = ProductDTO.builder().rating(5).build();


        assertTrue(validator.validateProperty(rating, "rating").isEmpty());
        assertTrue(validator.validateProperty(ratingZero, "rating").isEmpty());
        assertTrue(validator.validateProperty(ratingFive, "rating").isEmpty());
    }

    @Test
    protected void ratingInvalid() {
        ProductDTO ratingNegative = ProductDTO.builder().rating(-0.1).build();
        ProductDTO ratingMoreThanFive = ProductDTO.builder().rating(5.1).build();

        assertFalse(validator.validateProperty(ratingNegative, "rating").isEmpty());
        assertFalse(validator.validateProperty(ratingMoreThanFive, "rating").isEmpty());
    }

    @Test
    protected void stockQuantityValid() {
        ProductDTO stockQuantity = ProductDTO.builder().stockQuantity(10).build();
        ProductDTO stockQuantityZero = ProductDTO.builder().stockQuantity(0).build();

        assertTrue(validator.validateProperty(stockQuantity, "stockQuantity").isEmpty());
        assertTrue(validator.validateProperty(stockQuantityZero, "stockQuantity").isEmpty());
    }

    @Test
    protected void stockQuantityInvalid() {
        ProductDTO stockQuantityNegative = ProductDTO.builder().stockQuantity(-1).build();

        assertFalse(validator.validateProperty(stockQuantityNegative, "stockQuantity").isEmpty());
    }

    @Test
    protected void complexityValid() {
        ProductDTO complexityValid = ProductDTO.builder().complexity(1.25).build();

        assertTrue(validator.validateProperty(complexityValid, "complexity").isEmpty());
    }

    @Test
    protected void complexityInvalid() {
        ProductDTO lessThanZero = ProductDTO.builder().complexity(-0.1).build();
        ProductDTO moreThanFive = ProductDTO.builder().complexity(6).build();

        assertFalse(validator.validateProperty(lessThanZero, "complexity").isEmpty());
        assertFalse(validator.validateProperty(moreThanFive, "complexity").isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "product 1, product 1, true",
            "product 1, product 2, false"
    })
    void testEqualsObject(String name1, String name2, boolean equals) {

        ProductDTO p1 = new ProductDTO();
        p1.setName(name1);

        ProductDTO p2 = new ProductDTO();
        p2.setName(name2);

        assertEquals(equals, p1.equals(p2));
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