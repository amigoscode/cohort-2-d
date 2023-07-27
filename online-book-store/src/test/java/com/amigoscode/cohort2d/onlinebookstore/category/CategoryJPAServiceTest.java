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
    void shouldThrowExceptionWhenCategoryExistsByName() {
        // Given
        String name = "Thriller";
        String description = "Thrilling books";

        // When
        underTest.existsCategoryByName(name);

        // Then
        verify(categoryRepository).existsCategoryByName(name);
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
}