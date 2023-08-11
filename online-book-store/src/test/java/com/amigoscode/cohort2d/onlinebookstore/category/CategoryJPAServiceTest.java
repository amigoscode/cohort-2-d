package com.amigoscode.cohort2d.onlinebookstore.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryJPAServiceTest {

    private CategoryJPAService underTest;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        underTest = new CategoryJPAService(categoryRepository);
    }

    @Test
    void shouldFindAllCategories() {
        // Given && When
        underTest.findAllCategories();

        // Then
        verify(categoryRepository).findAll();
    }

    @Test
    void shouldAddCategory() {
        // Given
        Category category = new Category(1L, "Thriller", "Thrilling books");

        // When
        underTest.addCategory(category);

        // Then
        verify(categoryRepository).save(category);
    }

    @Test
    void shouldGetCategoryById() {
        // Given
        Long id = 10L;

        // When
        underTest.findById(id);

        // Then
        verify(categoryRepository).findById(id);

    }

    @Test
    void shouldUpdateCategory() {
        // Given
        Category category = new Category(1L, "Autobiography", "Stories of people's lives from their point of view");

        // When
        underTest.updateCategory(category);

        // Then
        verify(categoryRepository).save(category);

    }

    @Test
    void shouldDeleteCategoryById() {
        // Given
        Long id = 1L;

        // When
        underTest.deleteCategoryById(id);

        // Then
        verify(categoryRepository).deleteById(id);

    }


    @Test
    void shouldCheckCategoryExistsById() {
        // Given
        Long id = 1L;

        // When
        underTest.existsCategoryById(id);

        // Then
        verify(categoryRepository).existsById(id);

    }

    @Test
    void shouldCheckWhenCategoryExistsByName() {
        // Given
        String name = "Thriller";
        String description = "Thrilling books";

        // When
        underTest.existsCategoryByName(name);

        // Then
        verify(categoryRepository).existsCategoryByName(name);

    }
}