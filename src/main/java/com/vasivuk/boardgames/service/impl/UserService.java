package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.model.User;
import com.vasivuk.boardgames.repository.UserRepository;
import com.vasivuk.boardgames.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User login(User user) {
        return null;
    }

    @Override
    public void logout(User user) {

    }
}
