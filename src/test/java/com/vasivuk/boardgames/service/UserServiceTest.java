package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.dto.AppUserDTO;
import com.vasivuk.boardgames.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        AppUser user = AppUser.builder()
                .email("marko@gmail.com")
                .firstName("Marko")
                .lastName("Markovic")
                .password("1234")
                .userRole("USER")
                .build();

        AppUser userInDatabase = AppUser.builder()
                .email("filip@gmail.com")
                .firstName("Filip")
                .lastName("Filipovic")
                .password("1234")
                .userRole("USER")
                .build();

        List<AppUser> usersInDatabase = new ArrayList<>();
        usersInDatabase.add(userInDatabase);

        Mockito.when(userRepository.save(ArgumentMatchers.any(AppUser.class))).thenReturn(user);
        //Doesn't exist in database
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        //Exists in database
        Mockito.when(userRepository.findByEmail(userInDatabase.getEmail())).thenReturn(Optional.of(userInDatabase));
        //Get users
        Mockito.when(userRepository.findAll()).thenReturn(usersInDatabase);
    }

    @Test
    void testSaveUser() throws EntityAlreadyExistsException {
        AppUserDTO userToSave = AppUserDTO.builder()
                .email("marko@gmail.com")
                .firstName("Marko")
                .lastName("Markovic")
                .password("1234")
                .build();
        AppUser savedUser = userService.saveUser(userToSave);
        assertEquals(savedUser.getEmail(), userToSave.getEmail());
    }

    @Test
    void testSaveUserEmailAlreadyExists() {
        AppUserDTO userToSave = AppUserDTO.builder()
                .email("filip@gmail.com")
                .firstName("Marko")
                .lastName("Markovic")
                .password("1234")
                .build();
        assertThrows(EntityAlreadyExistsException.class, () -> userService.saveUser(userToSave));
    }

    @Test
    void testAssignAdminRoleToUser() throws EntityNotFoundException {
        userService.assignAdminRoleToUser("filip@gmail.com");
        if (userRepository.findByEmail("filip@gmail.com").isPresent())
            assertEquals(userRepository.findByEmail("filip@gmail.com").get().getUserRole(), "ADMIN");
    }

    @Test
    void testAssignAdminRoleToUser_UserNotFound() {
        assertThrows(EntityNotFoundException.class, () -> userService.assignAdminRoleToUser("mare@gmail.com"));
    }

    @Test
    void testFindUserByEmail() throws EntityNotFoundException {
        assertEquals(userService.findUserByEmail("filip@gmail.com").getEmail(), "filip@gmail.com");
    }

    @Test
    void testFindUserByEmail_UserNotFound() {
        assertThrows(EntityNotFoundException.class,
                () -> userService.findUserByEmail("marko@gmail.com"));
    }

    @Test
    void testGetUsers() {
        assertEquals(userService.getUsers(), userRepository.findAll());
    }
}