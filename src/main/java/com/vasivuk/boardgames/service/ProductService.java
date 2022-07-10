package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Product;

import java.util.List;

public interface ProductService {

    /**
     * Vraća sve proizvode iz sistema
     * @return svi proizvodi
     */
    List<Product> findAllProducts();

    /**
     * Vraća proizvod na osnovu njegovog identifikacionog broja
     * @param id identifikacioni broj
     * @return proizvod iz baze
     */
    Product findProductById(Long id) throws EntityNotFoundException;

    /**
     * Čuva novi proizvod u bazi podataka
     * @param product novi proizvod
     * @return sačuvani proizvod
     */
    Product saveProduct(Product product) throws EntityAlreadyExistsException;

    /**
     * Ažurira proizvod u bazi podataka
     * @param product proizvod sa ažuriranim podacima
     * @return ažurirani proizvod
     */
    Product updateProduct(Long id, Product product) throws EntityNotFoundException;

    /**
     * Briše proizvod iz sistema
     * @param id proizvod koji se briše
     */
    void deleteProduct(Long id) throws EntityNotFoundException;
}
