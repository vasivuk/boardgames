package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.repository.CategoryRepository;
import com.vasivuk.boardgames.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }
}
