package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.repository.ProductRepository;
import com.vasivuk.boardgames.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Product saveProduct(Product newProduct) throws EntityAlreadyExistsException {
        Optional<Product> product = repository.findByNameIgnoreCase(newProduct.getName());
        if (product.isPresent()) {
            throw new EntityAlreadyExistsException("Product with name: " + newProduct.getName() + " already exists");
        }
        return repository.save(newProduct);
    }

    public Product updateProduct(Long id, Product product) throws EntityNotFoundException {
        Optional<Product> oldProduct = repository.findById(id);
        if (oldProduct.isEmpty()) {
            throw new EntityNotFoundException("Product doesn't exist.");
        }
        oldProduct.get().setName(product.getName());
        oldProduct.get().setComplexity(product.getComplexity());
        oldProduct.get().setDescription(product.getDescription());
        oldProduct.get().setPrice(product.getPrice());
        oldProduct.get().setGameTime(product.getGameTime());
        oldProduct.get().setNumberOfPlayers(product.getNumberOfPlayers());
        oldProduct.get().setRating(product.getRating());

        return repository.save(oldProduct.get());
    }

    public void deleteProduct(Long id) throws EntityNotFoundException {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product doesn't exist.");
        }
        repository.deleteById(id);
    }
}
