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
        log.info("Fetching all categories");
        return repository.findAll();
    }

    @Override
    public List<Category> findCategoriesByCriteria(Category category) {
        return null;
    }

    @Override
    public Category createCategory(Category newCategory) throws EntityAlreadyExistsException {
        Optional<Category> category = repository.findByNameIgnoreCase(newCategory.getName());
        if (category.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Category with name: {} already exists.", newCategory.getName()));
        }
        return repository.save(newCategory);
    }

    @Override
    public Category updateCategory(Category category) throws EntityNotFoundException{
        Optional<Category> optionalCategory = repository.findById(category.getCategoryId());
        if (!optionalCategory.isPresent()) {
            throw new EntityNotFoundException("Category not found!");
        }
        optionalCategory.get().setName(category.getName());
        return optionalCategory.get();
    }

    @Override
    public void deleteCategory(Category category) {
        repository.delete(category);
    }

    @Override
    public List<Category> fetchCategoriesByName(String categoryName) throws EntityNotFoundException {
        List<Category> categories = repository.findByNameContainingIgnoreCase(categoryName);
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("No categories containing: " + categoryName);
        }
        return repository.findByNameContainingIgnoreCase(categoryName);
    }

}