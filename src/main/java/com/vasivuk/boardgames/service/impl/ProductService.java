package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.repository.ProductRepository;
import com.vasivuk.boardgames.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
}
