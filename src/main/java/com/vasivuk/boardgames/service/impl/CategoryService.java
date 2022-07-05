package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.repository.CategoryRepository;
import com.vasivuk.boardgames.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> findAllCategories() {
        return repository.findAll();
    }

    @Override
    public List<Category> findCategoriesByCriteria(Category category) {
        return null;
    }

    @Override
    public void createCategory(Category category) {
        repository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        Optional<Category> optionalCategory = repository.findById(category.getId());
        if(optionalCategory.isPresent()) {
            optionalCategory.get().setName(category.getName());
        }
    }

    @Override
    public void deleteCategory(Category category) {
        repository.delete(category);
    }

}
