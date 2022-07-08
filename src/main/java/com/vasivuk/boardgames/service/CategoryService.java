package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();

    List<Category> findCategoriesByCriteria(Category category);

    Category createCategory(Category category) throws EntityAlreadyExistsException;

    Category updateCategory(Category category) throws EntityNotFoundException;

    void deleteCategory(Category category);

    List<Category> fetchCategoriesByName(String categoryName) throws EntityNotFoundException;


}
