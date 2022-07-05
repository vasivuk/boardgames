package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.UserRole;

import java.util.List;

public interface IUserService {
    AppUser saveUser(AppUser appUser);

    UserRole saveRole(UserRole level);

    void assignRoleToUser(String email, String roleName);

    AppUser getUser(String email);

    List<AppUser> getUsers();
}
