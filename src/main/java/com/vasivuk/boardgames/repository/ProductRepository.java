package com.vasivuk.boardgames.repository;

import com.vasivuk.boardgames.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
