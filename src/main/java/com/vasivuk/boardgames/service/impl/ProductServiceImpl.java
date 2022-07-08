package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.repository.ProductRepository;
import com.vasivuk.boardgames.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public List<Product> findAllProducts() {
        log.info("Fetching all products");
        return repository.findAll();
    }

    public Product findProductById(Long id) {
        return null;
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Product product) {
        Product oldProduct = repository.findById(product.getProductId()).get();
        if(oldProduct == null) {
            throw new IllegalArgumentException(String.format("Product with id: %s does not exist in system", product.getProductId()));
        }
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
