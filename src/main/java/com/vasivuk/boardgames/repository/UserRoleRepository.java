package com.vasivuk.boardgames.repository;

import com.vasivuk.boardgames.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(String name);
}
