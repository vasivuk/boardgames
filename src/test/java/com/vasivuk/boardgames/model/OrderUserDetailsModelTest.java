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

@SpringBootTest(classes = OrderUserDetails.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = BoardgamesApplication.class)
class OrderUserDetailsModelTest extends ModelTest<OrderUserDetails> {

    @Override
    @ParameterizedTest
    @MethodSource("generateValidEntity")
    protected void testWhenValidEntity(OrderUserDetails entity) {
        assertTrue(validator.validateProperty(entity, "firstName").isEmpty());
        assertTrue(validator.validateProperty(entity, "lastName").isEmpty());
        assertTrue(validator.validateProperty(entity, "email").isEmpty());
        assertTrue(validator.validateProperty(entity, "country").isEmpty());
        assertTrue(validator.validateProperty(entity, "city").isEmpty());
        assertTrue(validator.validateProperty(entity, "address").isEmpty());
    }

    @Override
    @ParameterizedTest
    @MethodSource("generateInvalidEntity")
    protected void testWhenInvalidEntity(OrderUserDetails entity) {
        assertFalse(validator.validateProperty(entity, "firstName").isEmpty());
        assertFalse(validator.validateProperty(entity, "lastName").isEmpty());
        assertFalse(validator.validateProperty(entity, "email").isEmpty());
        assertFalse(validator.validateProperty(entity, "country").isEmpty());
        assertFalse(validator.validateProperty(entity, "city").isEmpty());
        assertFalse(validator.validateProperty(entity, "address").isEmpty());
    }

    private static Stream<Arguments> generateValidEntity() {
        return Stream.of(
                Arguments.of(new OrderUserDetails("Marko", "Markovic", "marko@gmail.com",
                        "Serbia", "Belgrade", "Adresa"))
        );
    }

    private static Stream<Arguments> generateInvalidEntity() {
        return Stream.of(
                Arguments.of(new OrderUserDetails(null, null, null,
                        null, null, null)),
                Arguments.of(new OrderUserDetails("", "", "",
                        "", "", ""))
        );
    }
}