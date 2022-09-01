package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.model.dto.ProductDTO;
import com.vasivuk.boardgames.repository.ProductRepository;
import com.vasivuk.boardgames.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public List<Product> findAllProducts() {
        log.info("Fetching all products");
        return repository.findAll();
    }

    public Product findProductById(Long id) throws EntityNotFoundException {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product doesn't exist.");
        }
        return product.get();
    }

    public Product saveProduct(ProductDTO newProduct) throws EntityAlreadyExistsException {
        Optional<Product> product = repository.findByNameIgnoreCase(newProduct.getName());
        if (product.isPresent()) {
            throw new EntityAlreadyExistsException("Product with name: " + newProduct.getName() + " already exists");
        }
        Product productToSave = Product.builder()
                .name(newProduct.getName())
                .description(newProduct.getDescription())
                .complexity(newProduct.getComplexity())
                .imageUrl(newProduct.getImageUrl())
                .gameTime(newProduct.getGameTime())
                .numberOfPlayers(newProduct.getNumberOfPlayers())
                .rating(newProduct.getRating())
                .price(newProduct.getPrice())
                .categories(newProduct.getCategories())
                .stockQuantity(newProduct.getStockQuantity())
                .build();
        productToSave.setSlug(newProduct.getName().toLowerCase().replace(" ", "-"));
        return repository.save(productToSave);
    }

    public Product updateProduct(Long id, Product product) throws EntityNotFoundException, EntityAlreadyExistsException {
        Optional<Product> oldProduct = repository.findById(id);
        if (oldProduct.isEmpty()) {
            throw new EntityNotFoundException("Product doesn't exist.");
        }
        Optional<Product> productWithThatName = repository.findByNameIgnoreCase(product.getName());
        if (productWithThatName.isPresent() && !productWithThatName.get().getName().equals(oldProduct.get().getName())) {
            throw new EntityAlreadyExistsException("Product with name: " + product.getName() + " already exists.");
        }
        oldProduct.get().setName(product.getName());
        oldProduct.get().setComplexity(product.getComplexity());
        oldProduct.get().setDescription(product.getDescription());
        oldProduct.get().setPrice(product.getPrice());
        oldProduct.get().setGameTime(product.getGameTime());
        oldProduct.get().setNumberOfPlayers(product.getNumberOfPlayers());
        oldProduct.get().setRating(product.getRating());
        oldProduct.get().setImageUrl(product.getImageUrl());
        oldProduct.get().setSlug(product.getName().toLowerCase().replace(" ", "-"));
        oldProduct.get().setCategories(product.getCategories());
        oldProduct.get().setStockQuantity(product.getStockQuantity());

        return repository.save(oldProduct.get());
    }

    public void deleteProduct(Long id) throws EntityNotFoundException {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product doesn't exist.");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Product> findProducts(Optional<String> pmin, Optional<String> pmax,
                                      Optional<String> tmin, Optional<String> tmax, Optional<String> name) {
        if (pmin.isPresent() && pmax.isPresent() && tmin.isPresent() && tmax.isPresent()) {
            BigDecimal priceMin = new BigDecimal((pmin.get()));
            BigDecimal priceMax = new BigDecimal((pmax.get()));
            int gameTimeMin = Integer.parseInt(tmin.get());
            int gameTimeMax = Integer.parseInt(tmax.get());
            return repository.findProductsByPriceAndTime(priceMin, priceMax, gameTimeMin, gameTimeMax);
        } else if(name.isPresent()) {
            return repository.findAllByNameContainingIgnoreCase(name.get());
        }
        return repository.findAll();
    }
}
