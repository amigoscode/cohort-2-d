package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private CategoryService underTest;

    @Mock
    private CategoryDAO categoryDAO;

    @BeforeEach
    void setUp() {
        underTest = new CategoryService(categoryDAO);
    }

    @Test
    void shouldGetAllCategories() {
        // Given
        Category category1 = new Category(1L, "Horror", "Scary stories");
        Category category2 = new Category(2L, "Mystery", "Mystery books");

        given(categoryDAO.findAllCategories()).willReturn(List.of(category1,category2));

        // When
        List<CategoryDTO> categoryList = underTest.getCategories();

        // Then
        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(2);

    }

    @Test
    void shouldAddCategory() {
        // Given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Horror", "Scary stories");
        when(categoryDAO.existsCategoryByName("Horror")).thenReturn(false);

        // When
        underTest.addCategory(categoryDTO);

        // Verify that the methods were called with the expected arguments
        verify(categoryDAO).existsCategoryByName("Horror");
        verify(categoryDAO, times(1)).addCategory(any(Category.class));
    }

    @Test
    void shouldThrowWhenAddCategoryWithExistingName() {
        // Given
        String name = "Horror";
        String description = "Scary books";
        CategoryDTO request = new CategoryDTO( 1L, name, description);

        given(categoryDAO.existsCategoryByName(name)).willReturn(true);

        // When && Then
        assertThatThrownBy(() -> underTest.addCategory(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Category with name [%s] already exists.".formatted(name));

        verify(categoryDAO, never()).addCategory(any());
    }
}