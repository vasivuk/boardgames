package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.repository.ProductRepository;
import com.vasivuk.boardgames.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService implements IProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAllProducts() {
        log.info("Fetching all products");
        return repository.findAll();
    }
}
