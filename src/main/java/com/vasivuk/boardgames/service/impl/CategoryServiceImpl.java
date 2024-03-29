package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.repository.CategoryRepository;
import com.vasivuk.boardgames.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> findAllCategories() {
        return repository.findAll();
    }

    @Override
    public Category createCategory(Category newCategory) throws EntityAlreadyExistsException {
        Optional<Category> category = repository.findByNameIgnoreCase(newCategory.getName());
        if (category.isPresent()) {
            throw new EntityAlreadyExistsException("Category with name: " + newCategory.getName() + " already exists");
        }
        return repository.save(newCategory);
    }

    @Override
    public Category updateCategory(Long id, Category category) throws EntityNotFoundException, EntityAlreadyExistsException {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new EntityNotFoundException("Category with name: " + category.getName() + " not found");
        }
        Optional<Category> categoryWithThatName = repository.findByNameIgnoreCase(category.getName());
        if (categoryWithThatName.isPresent() && !categoryWithThatName.get().getName().equals(optionalCategory.get().getName())) {
            throw new EntityAlreadyExistsException("Category with name: " + category.getName() + " already exists");
        }
        optionalCategory.get().setName(category.getName());
        optionalCategory.get().setDescription(category.getDescription());

        return repository.save(optionalCategory.get());
    }

    @Override
    public void deleteCategory(Long categoryId) {
        repository.deleteById(categoryId);
    }

    @Override
    public List<Category> fetchCategoriesByName(String categoryName) throws EntityNotFoundException {
        if(categoryName.isBlank()) {
            return repository.findAll();
        }
        List<Category> categories = repository.findAllByNameContainingIgnoreCase(categoryName);
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("No categories containing: " + categoryName);
        }
        return categories;
    }

    @Override
    public Category findCategoryById(Long id) throws EntityNotFoundException {
        Optional<Category> category = repository.findById(id);
        if(category.isEmpty()) {
            throw new EntityNotFoundException("Category with id: " + id + " not found");
        }
        return category.get();
    }
}
