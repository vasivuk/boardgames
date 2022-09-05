package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.model.dto.ProductDTO;
import com.vasivuk.boardgames.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.vasivuk.boardgames.configuration.Routes.*;


/**
 * ProductController kontroler obradjuje zahteve vezane za proizvode
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ProductController {

    /**
     * Servis u kom se nalazi poslovna logika vezana za proizvode
     */
    private final ProductService service;

    /**
     * Vraca proizvode, u zavisnosti od ulaznih parametara
     * @param pmin minimalna cena proizvoda
     * @param pmax maksimalna cena proizvoda
     * @param tmin minimalno srednje vreme trajanja partije
     * @param tmax maksimalno srednje vreme trajanja partije
     * @param name ime proizvoda
     * @return lista proizvoda koji zadovoljavaju uslove
     */
    @GetMapping(PRODUCT_COMMON)
    public List<Product> fetchProducts(@RequestParam Optional<String> pmin, @RequestParam Optional<String> pmax,
                                       @RequestParam Optional<String> tmin, @RequestParam Optional<String> tmax,
                                       @RequestParam Optional<String> name) {

        return service.findProducts(pmin, pmax, tmin, tmax, name);
    }


    /**
     * Metoda vraca proizvod po njegovim identifikacionim brojem
     * @param productId identifikacioni broj proizvoda
     * @return kreirani proizvod
     * @throws EntityNotFoundException u slucaju da ne postoji proizvod u bazi sa datim identifikacionim brojem
     */
    @GetMapping(PRODUCT_COMMON + ID)
    public Product findProductById(@PathVariable("id") Long productId) throws EntityNotFoundException {
        return service.findProductById(productId);
    }

    /**
     * Metoda unosi novi proizvod u sistem
     * @param product domenski objekat proizvod koji treba uneti u bazu
     * @return uneseni proizvod
     * @throws EntityAlreadyExistsException u slucaju da proizvod sa datim imenom vec postoji u bazi
     */
    @PostMapping(PRODUCT_COMMON + CREATE)
    public Product createProduct(@Valid @RequestBody ProductDTO product) throws EntityAlreadyExistsException {
        return service.saveProduct(product);
    }

    /**
     * Metoda azurira proizvod u sistemu
     * @param id identifikacioni broj proizvoda kog treba azurirati
     * @param product novi detalji proizvoda
     * @return azurirani proizvod
     * @throws EntityNotFoundException u slucaju da proizvod sa datim id brojem ne postoji u bazi
     * @throws EntityAlreadyExistsException u slucaju da novo ime vec postoji u bazi
     */
    @PutMapping(PRODUCT_COMMON + ID)
    public Product updateProduct(@PathVariable("id") Long id, @Valid  @RequestBody ProductDTO product) throws EntityNotFoundException, EntityAlreadyExistsException {
        return service.updateProduct(id, product);
    }

    /**
     * Metoda brise proizvod iz sistema
     * @param id identifikacioni broj proizvoda
     * @throws EntityNotFoundException u slucaju da proizvod sa datim id brojem ne postoji u bazi
     */
    @DeleteMapping(PRODUCT_COMMON + ID)
    public void deleteProduct(@PathVariable Long id) throws EntityNotFoundException {
        service.deleteProduct(id);
    }

}
