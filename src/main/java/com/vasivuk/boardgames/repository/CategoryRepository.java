package com.vasivuk.boardgames.repository;

import com.vasivuk.boardgames.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
