package com.vasivuk.boardgames.service;

import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.Category;
import com.vasivuk.boardgames.repository.CategoryRepository;
import com.vasivuk.boardgames.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CategoryServiceTest.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);
        category = new Category(1L, "Category", "description");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void Can_find_all_categories() {
        //when
        categoryService.findAllCategories();
        //then
        verify(categoryRepository).findAll();
    }

    @Test
    void Create_category_valid() throws EntityAlreadyExistsException {
        //given
        Category category = new Category(1L, "Category", "description");
        given(categoryRepository.findByNameIgnoreCase(category.getName())).willReturn(Optional.empty());
        given(categoryRepository.save(category)).willReturn(category);

        //when
        Category savedCategory = categoryService.createCategory(category);

        //then
        assertThat(savedCategory).isNotNull();
    }

    @Test
    void Create_category_not_valid_name_is_in_use() {
        //given
        given(categoryRepository.findByNameIgnoreCase(category.getName())).willReturn(Optional.of(category));

        //when
        assertThrows(EntityAlreadyExistsException.class, () -> categoryService.createCategory(category));

        //then
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void Update_when_new_category_valid() throws EntityAlreadyExistsException, EntityNotFoundException {
        given(categoryRepository.findById(category.getId())).willReturn(Optional.of(category));
        given(categoryRepository.findByNameIgnoreCase(category.getName())).willReturn(Optional.empty());
        given(categoryRepository.save(category)).willReturn(category);

        Category updatedCategory = categoryService.updateCategory(category.getId(), category);

        assertThat(updatedCategory).isNotNull();
    }

    @Test
    void Cannot_update_when_id_invalid() {
        //given
        given(categoryRepository.findById(category.getId())).willReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class, () -> categoryService.updateCategory(category.getId(), category));

        //then
        verify(categoryRepository, never()).save(category);
    }

    @Test
    void Cannot_update_when_new_name_invalid() {
        //given
        Category categoryDifferentName = new Category(2L, "Category 2", "desc");
        given(categoryRepository.findById(category.getId())).willReturn(Optional.of(category));
        given(categoryRepository.findByNameIgnoreCase(category.getName())).willReturn(Optional.of(categoryDifferentName));

        //when
        assertThrows(EntityAlreadyExistsException.class, () -> categoryService.updateCategory(category.getId(), category));

        //then
        verify(categoryRepository, never()).save(category);
    }

    @Test
    void Can_delete_category() {
        //when
        categoryService.deleteCategory(category.getId());
        //then
        verify(categoryRepository).deleteById(category.getId());
    }

    @Test
    void Can_fetch_categories_by_name() throws EntityNotFoundException {
        //given
        String name = "cat";
        given(categoryRepository.findAllByNameContainingIgnoreCase(name)).willReturn(List.of(category));
        //when
        categoryService.fetchCategoriesByName(name);
        //then
        verify(categoryRepository).findAllByNameContainingIgnoreCase(name);
    }

    @Test
    void No_categories_found_with_name() {
        //given
        String name = "cat";
        given(categoryRepository.findAllByNameContainingIgnoreCase(name)).willReturn(List.of());
        //then
        assertThrows(EntityNotFoundException.class, () -> categoryService.fetchCategoriesByName(name));
    }
    @Test
    void Can_find_category_by_id() throws EntityNotFoundException {
        //given
        Long id = 1L;
        given(categoryRepository.findById(id)).willReturn(Optional.of(category));
        //when
        Category foundCategory = categoryService.findCategoryById(id);
        //then
        assertThat(foundCategory).isEqualTo(category);
    }

    @Test
    void Cannot_find_category_by_id() {
        //given
        Long id = 1L;
        given(categoryRepository.findById(id)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> categoryService.findCategoryById(id));
    }
}