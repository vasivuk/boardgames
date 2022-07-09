package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.configuration.UserRole;
import com.vasivuk.boardgames.model.dto.AppUserDTO;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUserDTO appUser) throws EntityAlreadyExistsException;

    void assignAdminRoleToUser(String email);

    AppUser getUser(String email);

    List<AppUser> getUsers();
}
