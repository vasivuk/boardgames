package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Category;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();

    Category createCategory(Category category) throws EntityAlreadyExistsException;

    Category updateCategory(Long id, Category category) throws EntityNotFoundException, EntityAlreadyExistsException;

    void deleteCategory(Long categoryId);

    List<Category> fetchCategoriesByName(String categoryName) throws EntityNotFoundException;

    Category findCategoryById(Long id) throws EntityNotFoundException;
}
