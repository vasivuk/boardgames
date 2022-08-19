package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.model.dto.ProductDTO;
import com.vasivuk.boardgames.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.vasivuk.boardgames.configuration.Routes.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl service;

    @GetMapping(PRODUCT_COMMON)
    public List<Product> fetchProducts(@RequestParam Optional<String> pmin, @RequestParam Optional<String> pmax,
                                       @RequestParam Optional<String> tmin, @RequestParam Optional<String> tmax,
                                       @RequestParam Optional<String> name) {

        return service.findProducts(pmin, pmax, tmin, tmax, name);
    }


    @GetMapping(PRODUCT_COMMON + ID)
    public Product findProductById(@PathVariable("id") Long productId) throws EntityNotFoundException {
        return service.findProductById(productId);
    }

    @PostMapping(PRODUCT_COMMON + CREATE)
    public Product createProduct(@Valid @RequestBody ProductDTO product) throws EntityAlreadyExistsException {
        return service.saveProduct(product);
    }

    @PutMapping(PRODUCT_COMMON + ID)
    public Product updateProduct(@PathVariable("id") Long id, @Valid  @RequestBody Product product) throws EntityNotFoundException, EntityAlreadyExistsException {
        return service.updateProduct(id, product);
    }

    @DeleteMapping(PRODUCT_COMMON + ID)
    public void deleteProduct(@PathVariable Long id) throws EntityNotFoundException {
        service.deleteProduct(id);
    }

}
