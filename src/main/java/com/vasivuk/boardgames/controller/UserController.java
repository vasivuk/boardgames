package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.model.User;
import com.vasivuk.boardgames.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /*
    -CreateUser(User)
-Login(User)
-Logout(User)
-EditUserData(User)

     */
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/user")
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @PostMapping("/api/login")
    public User login(@RequestBody User user) {
        return service.login(user);
    }

    @PostMapping("/api/logout")
    public void logout(@RequestBody User user) {
        service.logout(user);
    }

}
