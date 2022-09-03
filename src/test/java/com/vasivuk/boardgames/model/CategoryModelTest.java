package com.vasivuk.boardgames.model;

import com.vasivuk.boardgames.BoardgamesApplication;
import com.vasivuk.boardgames.model.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
    protected void nameValid() {
        Category name = Category.builder().name("name").build();

        assertTrue(validator.validateProperty(name, "name").isEmpty());
    }

    @Test
    protected void nameInvalid() {
        Category nameNull = Category.builder().name(null).build();
        Category nameBlank = Category.builder().name("   ").build();
        Category nameMoreThan30 = Category.builder().name("x".repeat(31)).build();

        assertFalse(validator.validateProperty(nameNull, "name").isEmpty());
        assertFalse(validator.validateProperty(nameBlank, "name").isEmpty());
        assertFalse(validator.validateProperty(nameMoreThan30, "name").isEmpty());
    }

    @Test
    protected void descriptionValid() {
        Category description = Category.builder().description("Some description").build();
        Category descriptionBlank = Category.builder().description("   ").build();

        assertTrue(validator.validateProperty(description, "description").isEmpty());
        assertTrue(validator.validateProperty(descriptionBlank, "description").isEmpty());
    }

    @Test
    protected void descriptionInvalid() {
        Category descriptionOver1000 = Category.builder().description(" ".repeat(1001)).build();
        assertFalse(validator.validateProperty(descriptionOver1000, "name").isEmpty());
    }

    @Test
    void testToString() {
        Category c = new Category();
        c.setName("Action");
        c.setDescription("Something");

        String s = c.toString();

        assertTrue(s.contains("Action"));
        assertTrue(s.contains("Something"));
    }

    @ParameterizedTest
    @CsvSource({
            "Action, desc, Action, desc, true",
            "Action, desc, Adventure, desc, false",
            "Action, desc, Adventure, desc, false",
            "Action, desc, Action, other description, true"
    })
    void testEqualsObject(String name1, String description1,
                          String name2, String description2, boolean equals) {

        Category c1 = new Category();
        c1.setName(name1);
        c1.setDescription(description1);

        Category c2 = new Category();
        c2.setName(name2);
        c2.setDescription(description2);

        assertEquals(equals, c1.equals(c2) );
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