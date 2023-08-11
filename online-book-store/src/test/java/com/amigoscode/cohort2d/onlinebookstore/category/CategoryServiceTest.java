package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.RequestValidationException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import com.github.javafaker.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
    void shouldGetCategoryById() {
        // Given
        Long id = 1L;
        Category category = new Category(id, "Autobiography", "Stories of people's lives from their point of view");
        given(categoryDAO.findById(id)).willReturn(Optional.of(category));

        CategoryDTO expected = CategoryDTOMapper.INSTANCE.modelToDTO(category);

        // When
        CategoryDTO actual = underTest.getCategoryById(id);

        // Then
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.name()).isEqualTo(expected.name());
        assertThat(actual.description()).isEqualTo(expected.description());
    }

    @Test
    void shouldUpdateCategoryById() {
        // Given
        Long id = 1L;
        Category category = new Category(id, "Horror", "Scary stories");
        given(categoryDAO.findById(id)).willReturn(Optional.of(category));

        CategoryDTO request = new CategoryDTO(id, "Horror Classics", "Scariest stories of all time");

        // When
        underTest.updateCategory(id, request);

        // Then
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryDAO).updateCategory(categoryArgumentCaptor.capture());

        Category capturedCategory = categoryArgumentCaptor.getValue();

        assertThat(capturedCategory.getId()).isEqualTo(request.id());
        assertThat(capturedCategory.getName()).isEqualTo(request.name());
        assertThat(capturedCategory.getDescription()).isEqualTo(request.description());

    }

    @Test
    void shouldDeleteCategoryById() {
        // Given
        Long id = 10L;
        given(categoryDAO.existsCategoryById(id)).willReturn(true);

        // When
        underTest.deleteCategoryById(id);

        // Then
        verify(categoryDAO).deleteCategoryById(id);
    }

    @Test
    void shouldThrowWhenIdNotFound() {
        // Given
        Long id = 10L;

        // When && Then
        assertThatThrownBy(() -> underTest.getCategoryById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category with id [%s] not found.".formatted(id));

    }

    @Test
    void shouldThrowWhenDeletingNonExistentCategory() {
        // Given
        Long id = 10L;

        // When && Then
        assertThatThrownBy(() -> underTest.deleteCategoryById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category with id [%s] not found.".formatted(id));

        verify(categoryDAO, never()).deleteCategoryById(id);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentCategory() {
        // Given
        Long id = 1L;
        CategoryDTO request = new CategoryDTO(id, "Horror Classics", "Scariest stories of all time");

        // When && Then
        assertThatThrownBy(() -> underTest.updateCategory(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category with id [%s] not found.".formatted(id));

        verify(categoryDAO, never()).updateCategory(any());
    }

    @Test
    void shouldThrowWhenUpdatingWhenNoChangesFound() {
        // Given
        Long id = 1L;
        CategoryDTO request = new CategoryDTO(id, "Horror Classics", "Scariest stories of all time");
        Category existingCategory = new Category(id, "Horror Classics", "Scariest stories of all time");

        when(categoryDAO.findById(id)).thenReturn(Optional.of(existingCategory));


        // When && Then
        assertThatThrownBy(() -> underTest.updateCategory(id, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No data changes found.");

        verify(categoryDAO, never()).updateCategory(any());
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