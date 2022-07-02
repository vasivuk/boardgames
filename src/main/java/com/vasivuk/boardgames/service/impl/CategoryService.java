package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.repository.CategoryRepository;
import com.vasivuk.boardgames.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createCategory(Category category) {
        repository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }
}
