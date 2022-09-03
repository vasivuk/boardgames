package com.vasivuk.boardgames.model;

import com.vasivuk.boardgames.BoardgamesApplication;
import com.vasivuk.boardgames.model.dto.AppUserDTO;
import org.junit.jupiter.api.Test;
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
    protected void countryValid() {
        OrderUserDetails country = OrderUserDetails.builder().country("Serbia").build();

        assertTrue(validator.validateProperty(country, "country").isEmpty());
    }

    @Test
    protected void countryInvalid() {
        OrderUserDetails countryBlank = OrderUserDetails.builder().country("   ").build();

        assertFalse(validator.validateProperty(countryBlank, "country").isEmpty());
    }

    @Test
    protected void cityValid() {
        OrderUserDetails city = OrderUserDetails.builder().city("Beograd").build();

        assertTrue(validator.validateProperty(city, "city").isEmpty());
    }

    @Test
    protected void cityInvalid() {
        OrderUserDetails cityBlank = OrderUserDetails.builder().city("   ").build();

        assertFalse(validator.validateProperty(cityBlank, "city").isEmpty());
    }

    @Test
    protected void addressValid() {
        OrderUserDetails address = OrderUserDetails.builder().address("Serbia").build();

        assertTrue(validator.validateProperty(address, "address").isEmpty());
    }

    @Test
    protected void addressInvalid() {
        OrderUserDetails addressBlank = OrderUserDetails.builder().address("   ").build();

        assertFalse(validator.validateProperty(addressBlank, "address").isEmpty());
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