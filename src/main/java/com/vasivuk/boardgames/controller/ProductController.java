package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.service.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.vasivuk.boardgames.configuration.Routes.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping(PRODUCT_COMMON)
    private List<Product> getAllProducts() {
        return service.findAllProducts();
    }

}
