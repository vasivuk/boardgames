package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.dto.AppUserDTO;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUserDTO appUser) throws EntityAlreadyExistsException;

    void assignAdminRoleToUser(String email) throws EntityNotFoundException;

    AppUser findUserByEmail(String email) throws EntityNotFoundException;

    List<AppUser> getUsers();
}
