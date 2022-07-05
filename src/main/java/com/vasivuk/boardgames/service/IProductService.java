package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAllProducts();
}
