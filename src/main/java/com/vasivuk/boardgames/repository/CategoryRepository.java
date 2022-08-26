package com.vasivuk.boardgames.repository;

import com.vasivuk.boardgames.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByNameContainingIgnoreCase(String name);

    Optional<Category> findByNameIgnoreCase(String name);
}
