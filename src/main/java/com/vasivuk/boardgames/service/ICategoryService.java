package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.model.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findAllCategories();

    List<Category> findCategoriesByCriteria(Category category);

    void createCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Category category);
}
