package com.vasivuk.boardgames.repository;

import com.vasivuk.boardgames.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContainingIgnoreCase(String name);

    Optional<Category> findByNameIgnoreCase(String name);
}
