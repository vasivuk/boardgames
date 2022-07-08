package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.vasivuk.boardgames.configuration.Routes.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl service;

    @GetMapping(PRODUCT_COMMON)
    public List<Product> getAllProducts() {
        return service.findAllProducts();
    }

    @PostMapping(PRODUCT_COMMON+CREATE)
    public Product createProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PutMapping(PRODUCT_COMMON)
    public Product updateProduct(@RequestBody Product product) {
        return service.updateProduct(product);
    }

    @DeleteMapping(PRODUCT_COMMON+ID)
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }

}
