package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.vasivuk.boardgames.configuration.Routes.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    /*
    -GetAllCategories(List<Category>)
    -FindCategories(Category, List<Category>)
    -LoadCategory(Category)
    -CreateCategory(Category)
    -UpdateCategory(Category)
    -DeleteCategory(Category)
     */
    private final CategoryService categoryService;

    @GetMapping(CATEGORY_COMMON)
    public List<Category> fetchCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping(CATEGORY_COMMON + NAME)
    public List<Category> fetchCategoriesByName(@RequestParam("search") String categoryName) throws EntityNotFoundException {
        return categoryService.fetchCategoriesByName(categoryName);
    }

    @PostMapping(CATEGORY_COMMON + CREATE)
    public Category createCategory(@RequestBody @Valid Category category) throws EntityAlreadyExistsException {
        return categoryService.createCategory(category);
    }

}
