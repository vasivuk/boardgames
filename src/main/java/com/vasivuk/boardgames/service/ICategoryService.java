package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.model.Category;

import java.util.List;

public interface ICategoryService {
    void createCategory(Category category);

    List<Category> findAll();
}
