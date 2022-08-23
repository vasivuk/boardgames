package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static com.vasivuk.boardgames.configuration.Routes.*;

@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<List<Category>> fetchCategories(HttpServletResponse response) {

        response.addCookie(new Cookie("my", "cookie"));

        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping(CATEGORY_COMMON + NAME)
    public List<Category> fetchCategoriesByName(@RequestParam("search") String categoryName) throws EntityNotFoundException {
        return categoryService.fetchCategoriesByName(categoryName);
    }

    @GetMapping(CATEGORY_COMMON + ID)
    public Category findCategoryById(@PathVariable("id") Long id) throws EntityNotFoundException{
        return categoryService.findCategoryById(id);
    }

    @DeleteMapping(CATEGORY_COMMON + ID)
    public void deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @PutMapping(CATEGORY_COMMON + ID)
    public Category updateCategory(@PathVariable("id") Long categoryId , @Valid @RequestBody Category category)
            throws EntityNotFoundException, EntityAlreadyExistsException {
        return categoryService.updateCategory(categoryId, category);
    }

    @PostMapping(CATEGORY_COMMON + CREATE)
    public Category createCategory(@RequestBody @Valid Category category) throws EntityAlreadyExistsException {
        return categoryService.createCategory(category);
    }
}
