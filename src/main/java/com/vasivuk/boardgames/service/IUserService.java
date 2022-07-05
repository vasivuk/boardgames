package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.model.AppUser;

public interface IUserService {
    AppUser createUser(AppUser appUser);

    AppUser login(AppUser appUser);

    void logout(AppUser appUser);
}
