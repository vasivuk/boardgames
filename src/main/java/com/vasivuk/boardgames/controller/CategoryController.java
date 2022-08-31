package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static com.vasivuk.boardgames.configuration.Routes.*;

/**
 * CategoryController obradjuje zahteve vezane za kategorije proizvoda
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    /**
     * Servis ukojem se nalazi logika vezana za kategorije
     */
    private final CategoryService categoryService;

    /**
     * Metoda vraca sve kategorije iz baze
     * @param response serverski odgovor
     * @return Serverski odgovor 200 ako su pronadjene kategorije
     */
    @GetMapping(CATEGORY_COMMON)
    public ResponseEntity<List<Category>> fetchCategories(HttpServletResponse response) {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    /**
     * Pronalazi listu kategorija koje sadrze String karaktera u svom imenu
     * @param categoryName String karaktera koji treba da se nalazi u imenu kategorija
     * @return lista kategorija koja ispunjava uslov
     * @throws EntityNotFoundException baca se u slucaju da nije pronadjena nijedna kategorija sa odredjenim Stringom
     */
    @GetMapping(CATEGORY_COMMON + NAME)
    public List<Category> fetchCategoriesByName(@RequestParam("search") String categoryName) throws EntityNotFoundException {
        return categoryService.fetchCategoriesByName(categoryName);
    }

    /**
     * Metoda pronalazi kategoriju sa odredjenim identifikacionim brojem
     * @param id identifikacioni broj kategorije
     * @return kategorija sa odredjenim ID-em
     * @throws EntityNotFoundException baca se u slucaju da nije pronadjena kategorija sa unesenim ID parametrom.
     */
    @GetMapping(CATEGORY_COMMON + ID)
    public Category findCategoryById(@PathVariable("id") Long id) throws EntityNotFoundException{
        return categoryService.findCategoryById(id);
    }

    /**
     * Metoda brise odredjenu kategoriju iz sistema.
     * @param categoryId id kategorije koja treba da bude obrisana
     */
    @DeleteMapping(CATEGORY_COMMON + ID)
    public void deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    /**
     * Metoda azurira kategoriju, na osnovu id-a i dopremljenih novih informacija o kategoriji
     * @param categoryId identifikacioni broj kategorije
     * @param category novi podaci o kategoriji
     * @return azurirana kategorija
     * @throws EntityNotFoundException ako kategorija ne postoji u bazi
     * @throws EntityAlreadyExistsException ako novo ime kategorije vec postoji u bazi
     */
    @PutMapping(CATEGORY_COMMON + ID)
    public Category updateCategory(@PathVariable("id") Long categoryId , @Valid @RequestBody Category category)
            throws EntityNotFoundException, EntityAlreadyExistsException {
        return categoryService.updateCategory(categoryId, category);
    }

    /**
     * Metoda unosi novu kategoriju u sistem
     * @param category nova kategorija, bez id atributa
     * @return nova kategorija, unesena u sistem
     * @throws EntityAlreadyExistsException ako ime nove kategorije vec postoji u bazi
     */
    @PostMapping(CATEGORY_COMMON + CREATE)
    public Category createCategory(@RequestBody @Valid Category category) throws EntityAlreadyExistsException {
        return categoryService.createCategory(category);
    }
}
