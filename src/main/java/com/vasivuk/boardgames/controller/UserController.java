package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.model.AppUser;
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
    public AppUser createUser(@RequestBody AppUser appUser) {
        return service.createUser(appUser);
    }

    @PostMapping("/api/login")
    public AppUser login(@RequestBody AppUser appUser) {
        return service.login(appUser);
    }

    @PostMapping("/api/logout")
    public void logout(@RequestBody AppUser appUser) {
        service.logout(appUser);
    }

}
