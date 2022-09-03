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

    @Test
    protected void firstNameValid() {
        AppUserDTO name = AppUserDTO.builder().firstName("Marko").build();

        assertTrue(validator.validateProperty(name, "firstName").isEmpty());
    }

    @Test
    protected void firstNameInvalid() {
        AppUserDTO nameNull = AppUserDTO.builder().firstName(null).build();
        AppUserDTO nameBlank = AppUserDTO.builder().firstName("   ").build();
        AppUserDTO nameMoreThan30 = AppUserDTO.builder().firstName("x".repeat(31)).build();

        assertFalse(validator.validateProperty(nameNull, "firstName").isEmpty());
        assertFalse(validator.validateProperty(nameBlank, "firstName").isEmpty());
        assertFalse(validator.validateProperty(nameMoreThan30, "firstName").isEmpty());
    }

    @Test
    protected void lastNameValid() {
        AppUserDTO name = AppUserDTO.builder().lastName("Markovic").build();

        assertTrue(validator.validateProperty(name, "lastName").isEmpty());
    }

    @Test
    protected void lastNameInvalid() {
        AppUserDTO lastNameNull = AppUserDTO.builder().lastName(null).build();
        AppUserDTO lastNameBlank = AppUserDTO.builder().lastName("   ").build();
        AppUserDTO lastNameMoreThan30 = AppUserDTO.builder().lastName("x".repeat(31)).build();

        assertFalse(validator.validateProperty(lastNameNull, "lastName").isEmpty());
        assertFalse(validator.validateProperty(lastNameBlank, "lastName").isEmpty());
        assertFalse(validator.validateProperty(lastNameMoreThan30, "lastName").isEmpty());
    }

    @Test
    protected void emailValid() {
        AppUserDTO email = AppUserDTO.builder().email("marko@gmail.com").build();

        assertTrue(validator.validateProperty(email, "email").isEmpty());
    }

    @Test
    protected void emailInvalid() {
        AppUserDTO emailNull = AppUserDTO.builder().email(null).build();
        AppUserDTO emailBlank = AppUserDTO.builder().email("   ").build();
        AppUserDTO invalidEmail = AppUserDTO.builder().email("email").build();
        AppUserDTO invalidEmail2 = AppUserDTO.builder().email("email@").build();
        AppUserDTO invalidEmail3 = AppUserDTO.builder().email("@asd.com").build();

        assertFalse(validator.validateProperty(emailNull, "email").isEmpty());
        assertFalse(validator.validateProperty(emailBlank, "email").isEmpty());
        assertFalse(validator.validateProperty(invalidEmail, "email").isEmpty());
        assertFalse(validator.validateProperty(invalidEmail2, "email").isEmpty());
        assertFalse(validator.validateProperty(invalidEmail3, "email").isEmpty());
    }

    @Test
    protected void passwordValid() {
        AppUserDTO password = AppUserDTO.builder().password("asdasd").build();

        assertTrue(validator.validateProperty(password, "password").isEmpty());
    }

    @Test
    protected void passwordInvalid() {
        AppUserDTO passwordNull = AppUserDTO.builder().password(null).build();
        AppUserDTO passwordBlank = AppUserDTO.builder().password("   ").build();
        AppUserDTO passwordLessThan5 = AppUserDTO.builder().password("1234").build();

        assertFalse(validator.validateProperty(passwordNull, "password").isEmpty());
        assertFalse(validator.validateProperty(passwordBlank, "password").isEmpty());
        assertFalse(validator.validateProperty(passwordLessThan5, "password").isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "marko@gmail.com, marko@gmail.com, true",
            "marko@gmail.com, filip@gmail.com, false"
    })
    void testEqualsObject(String email1, String email2, boolean equals) {

        AppUserDTO user1 = new AppUserDTO();
        user1.setEmail(email1);

        AppUserDTO user2 = new AppUserDTO();
        user2.setEmail(email2);

        assertEquals(equals, user1.equals(user2));
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