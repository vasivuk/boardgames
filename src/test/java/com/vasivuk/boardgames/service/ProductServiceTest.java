package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.model.Product;
import com.vasivuk.boardgames.model.dto.ProductDTO;
import com.vasivuk.boardgames.repository.ProductRepository;
import com.vasivuk.boardgames.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ProductServiceTest.class)
class ProductServiceTest {

    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    private Product product;
    private ProductDTO productDTO;
    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Product")
                .description("Description")
                .price(new BigDecimal(50))
                .slug("product")
                .build();
        productDTO = ProductDTO.builder()
                .name("Product")
                .description("Description")
                .price(new BigDecimal(50))
                .build();
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void Can_find_all_products() {
        productService.findAllProducts();

        verify(productRepository).findAll();
    }

    @Test
    void Can_find_product_by_id() throws EntityNotFoundException {
        //given
        Long id = 1L;
        given(productRepository.findById(id)).willReturn(Optional.of(product));
        //when
        Product foundProduct = productService.findProductById(id);
        //then
        assertThat(foundProduct).isEqualTo(product);
    }

    @Test
    void Cannot_find_product_by_id() {
        //given
        Long id = 1L;
        given(productRepository.findById(id)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> productService.findProductById(id));
    }

    @Test
    void Cannot_save_product_already_exists() {
        //given
        given(productRepository.findByNameIgnoreCase(productDTO.getName())).willReturn(Optional.of(product));

        //when
        assertThrows(EntityAlreadyExistsException.class, () -> productService.saveProduct(productDTO));

        //then
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void Product_saved_successfully() throws EntityAlreadyExistsException {
        Product productToSave = Product.builder()
                .name("Product")
                .description("Description")
                .price(new BigDecimal(50))
                .slug("product")
                .build();
        //given
        given(productRepository.findByNameIgnoreCase(productDTO.getName())).willReturn(Optional.empty());
        given(productRepository.save(productToSave)).willReturn(productToSave);

        //when
        Product savedProduct = productService.saveProduct(productDTO);

        //then
        assertThat(savedProduct).isNotNull();
    }

    @Test
    void Product_updated_successfully() throws EntityAlreadyExistsException, EntityNotFoundException {
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(productRepository.findByNameIgnoreCase(product.getName())).willReturn(Optional.empty());
        given(productRepository.save(product)).willReturn(product);

        Product updatedProduct = productService.updateProduct(product.getId(), productDTO);

        assertThat(updatedProduct).isNotNull();
    }

    @Test
    void Cannot_update_product_when_id_invalid() {
        given(productRepository.findById(product.getId())).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> productService.updateProduct(product.getId(), productDTO));

        verify(productRepository, never()).save(product);

    }

    @Test
    void Cannot_update_product_when_new_name_invalid() {
        //given
        Product productDifferentName = Product.builder()
                .id(2L)
                .name("Product 2")
                .description("Description")
                .price(new BigDecimal(50))
                .slug("product-2")
                .build();
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(productRepository.findByNameIgnoreCase(product.getName())).willReturn(Optional.of(productDifferentName));

        //when
        assertThrows(EntityAlreadyExistsException.class,
                () -> productService.updateProduct(product.getId(), productDTO));

        //then
        verify(productRepository, never()).save(product);
    }
    @Test
    void Can_delete_product() throws EntityNotFoundException {
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        productService.deleteProduct(product.getId());

        verify(productRepository).deleteById(product.getId());
    }

    @Test
    void Delete_product_does_not_exist() {
        given(productRepository.findById(product.getId())).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(product.getId()));

        verify(productRepository, never()).deleteById(product.getId());
    }

    @Test
    void Find_products_price_and_time() {
        String pmin = "10";
        String pmax = "100";
        String tmin = "10";
        String tmax = "100";

        productService.findProducts(Optional.of(pmin), Optional.of(pmax),
                Optional.of(tmin), Optional.of(tmax), Optional.empty());

        verify(productRepository).findProductsByPriceAndTime(BigDecimal.valueOf(Integer.parseInt(pmin)),
                BigDecimal.valueOf(Integer.parseInt(pmax)),
                Integer.parseInt(tmin),
                Integer.parseInt(tmax));
    }

    @Test
    void Find_products_by_name() {
        String name = "name";

        productService.findProducts(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.of(name));

        verify(productRepository).findAllByNameContainingIgnoreCase(name);
    }

    @Test
    void Find_all_products() {
        String name = "name";

        productService.findProducts(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        verify(productRepository).findAll();
    }
}