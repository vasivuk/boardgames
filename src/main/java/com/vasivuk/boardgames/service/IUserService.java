package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.model.User;

public interface IUserService {
    User createUser(User user);

    User login(User user);

    void logout(User user);
}
