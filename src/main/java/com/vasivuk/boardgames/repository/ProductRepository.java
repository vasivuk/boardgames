package com.vasivuk.boardgames.repository;

import com.vasivuk.boardgames.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameIgnoreCase(String name);
    List<Product> findAllByNameContainingIgnoreCase(String name);

    @Query( value = "select p from Product p where " +
            "p.price > ?1 and " +
            "p.price <= ?2 and " +
            "p.gameTime >= ?3 and " +
            "p.gameTime <= ?4")
    List<Product> findProductsByPriceAndTime(BigDecimal priceMin, BigDecimal priceMax, int timeMin, int timeMax);
}
