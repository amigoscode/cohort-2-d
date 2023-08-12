package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        List<CategoryDTO> expected = CategoryDTOMapper.INSTANCE.modelToDTO((categoryDAO.findAllCategories()));

        // When
        List<CategoryDTO> actual = underTest.getCategories();

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldAddCategory() {
        // Given
        CategoryDTO request = new CategoryDTO(null, "Horror", "Scary stories");
        given(categoryDAO.existsCategoryByName("Horror")).willReturn(false);

        // When
        underTest.addCategory(request);

        // Then
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryDAO).addCategory(categoryArgumentCaptor.capture());

        Category capturedCategory = categoryArgumentCaptor.getValue();

        assertThat(capturedCategory.getId()).isNull();
        assertThat(capturedCategory.getName()).isEqualTo(request.name());
        assertThat(capturedCategory.getDescription()).isEqualTo(request.description());

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