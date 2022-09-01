package com.vasivuk.boardgames.model;

import com.vasivuk.boardgames.BoardgamesApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Category.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = BoardgamesApplication.class)
class CategoryModelTest extends ModelTest<Category> {

    @Override
    @ParameterizedTest
    @MethodSource("generateValidEntity")
    protected void testWhenValidEntity(Category entity) {
        assertTrue(validator.validateProperty(entity, "name").isEmpty());
        assertTrue(validator.validateProperty(entity, "description").isEmpty());
    }

    @Override
    @ParameterizedTest
    @MethodSource("generateInvalidEntity")
    protected void testWhenInvalidEntity(Category entity) {
        assertFalse(validator.validateProperty(entity, "name").isEmpty());
        assertFalse(validator.validateProperty(entity, "description").isEmpty());
    }

    private static Stream<Arguments> generateValidEntity() {
        return Stream.of(
                Arguments.of(new Category(1L, "Category 1", "Description")),
                Arguments.of(new Category(null, "Category 1", null))
        );
    }

    private static Stream<Arguments> generateInvalidEntity() {
        String description = " ".repeat(1001);
        return Stream.of(
                Arguments.of(new Category(null, null, description))
                );
    }
}