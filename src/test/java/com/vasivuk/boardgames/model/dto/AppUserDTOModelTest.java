package com.vasivuk.boardgames.model.dto;

import com.vasivuk.boardgames.BoardgamesApplication;
import com.vasivuk.boardgames.model.ModelTest;
import com.vasivuk.boardgames.model.OrderUserDetails;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AppUserDTO.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = BoardgamesApplication.class)
class AppUserDTOModelTest extends ModelTest<AppUserDTO> {

    @Override
    @ParameterizedTest
    @MethodSource("generateValidEntity")
    protected void testWhenValidEntity(AppUserDTO entity) {
        assertTrue(validator.validateProperty(entity, "firstName").isEmpty());
        assertTrue(validator.validateProperty(entity, "lastName").isEmpty());
        assertTrue(validator.validateProperty(entity, "email").isEmpty());
        assertTrue(validator.validateProperty(entity, "password").isEmpty());
    }

    @Override
    @ParameterizedTest
    @MethodSource("generateInvalidEntity")
    protected void testWhenInvalidEntity(AppUserDTO entity) {
        assertFalse(validator.validateProperty(entity, "firstName").isEmpty());
        assertFalse(validator.validateProperty(entity, "lastName").isEmpty());
        assertFalse(validator.validateProperty(entity, "email").isEmpty());
        assertFalse(validator.validateProperty(entity, "password").isEmpty());
    }

    private static Stream<Arguments> generateValidEntity() {
        return Stream.of(
                Arguments.of(AppUserDTO.builder()
                        .firstName("Marko")
                        .lastName("Markovic")
                        .email("admin@gmail.com")
                        .password("12345")
                        .build())
        );
    }

    private static Stream<Arguments> generateInvalidEntity() {
        return Stream.of(
                Arguments.of(AppUserDTO.builder()
                        .firstName(null)
                        .lastName(null)
                        .email(null)
                        .password(null)
                        .build()),
                Arguments.of(AppUserDTO.builder()
                        .firstName("")
                        .lastName("")
                        .email("")
                        .password("")
                        .build()),
                Arguments.of(AppUserDTO.builder()
                        .firstName("")
                        .lastName("")
                        .email("")
                        .password("123")
                        .build())
        );
    }
}