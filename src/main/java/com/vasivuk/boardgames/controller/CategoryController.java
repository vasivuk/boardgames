package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    /*
    -GetAllCategories(List<Category>)
    -FindCategories(Category, List<Category>)
    -LoadCategory(Category)
    -CreateCategory(Category)
    -UpdateCategory(Category)
    -DeleteCategory(Category)
     */
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/api/category")
    List<Category> getAllCategories() {
        return service.findAll();
    }

    @PostMapping("/api/category")
    ResponseEntity<String> createCategory(@RequestBody @Valid Category category) {
        service.createCategory(category);
        return ResponseEntity.ok("Category is valid");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
