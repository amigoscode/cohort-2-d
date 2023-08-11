package com.amigoscode.cohort2d.onlinebookstore.author;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.RequestValidationException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
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
class AuthorServiceTest {

    private AuthorService underTest;
    
    @Mock
    private AuthorDAO authorDAO;

    @BeforeEach
    void setUp() {
        underTest = new AuthorService(authorDAO);
    }

    @Test
    void shouldGetAllAuthors() {
        // Given
        Author author1 = new Author(1L, "John", "Doe");
        Author author2 = new Author(2L, "Jane", "Doe");

        given(authorDAO.findAllAuthors()).willReturn(List.of(author1,author2));

        List<AuthorDTO> expected = AuthorDTOMapper.INSTANCE.modelToDTO(authorDAO.findAllAuthors());

        // When
        List<AuthorDTO> actual = underTest.getAllAuthors();

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void shouldAddAuthor() {
        // Given
        AuthorDTO request = new AuthorDTO(1L, "John", "Doe");
        given(authorDAO.existsAuthorByName("John", "Doe")).willReturn(false);

        // When
        underTest.addAuthor(request);

        // Then
        ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);
        verify(authorDAO).addAuthor(authorArgumentCaptor.capture());

        Author capturedAuthor = authorArgumentCaptor.getValue();

        assertThat(capturedAuthor.getId()).isEqualTo(request.id());
        assertThat(capturedAuthor.getFirstName()).isEqualTo(request.firstName());
        assertThat(capturedAuthor.getLastName()).isEqualTo(request.lastName());

    }

    @Test
    void shouldGetAuthorById() {
        // Given
        Long id = 1L;
        Author author = new Author(id, "Jane", "Austin");
        given(authorDAO.findById(id)).willReturn(Optional.of(author));

        AuthorDTO expected = AuthorDTOMapper.INSTANCE.modelToDTO(author);

        // When
        AuthorDTO actual = underTest.getAuthorById(id);

        // Then
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
    }

    @Test
    void shouldUpdateAuthorById() {
        // Given
        Long id = 1L;
        Author author = new Author(id, "John", "Doe");
        given(authorDAO.findById(id)).willReturn(Optional.of(author));

        AuthorDTO request = new AuthorDTO(id, "Jane", "Dawson");

        // When
        underTest.updateAuthor(id, request);

        // Then
        ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);
        verify(authorDAO).updateAuthor(authorArgumentCaptor.capture());

        Author capturedAuthor = authorArgumentCaptor.getValue();

        assertThat(capturedAuthor.getId()).isEqualTo(request.id());
        assertThat(capturedAuthor.getFirstName()).isEqualTo(request.firstName());
        assertThat(capturedAuthor.getLastName()).isEqualTo(request.lastName());

    }

    @Test
    void shouldDeleteAuthorById() {
        // Given
        Long id = 10L;
        given(authorDAO.existsAuthorById(id)).willReturn(true);

        // When
        underTest.deleteAuthorById(id);

        // Then
        verify(authorDAO).deleteAuthorById(id);
    }

    @Test
    void shouldThrowWhenGetByIdNonExistentAuthor() {
        // Given
        Long id = 10L;

        // When && Then
        assertThatThrownBy(() -> underTest.getAuthorById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author with id [%s] not found.".formatted(id));

    }

    @Test
    void shouldThrowWhenDeletingNonExistentAuthor() {
        // Given
        Long id = 10L;

        // When && Then
        assertThatThrownBy(() -> underTest.deleteAuthorById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author with id [%s] not found.".formatted(id));

        verify(authorDAO, never()).deleteAuthorById(id);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentAuthor() {
        // Given
        Long id = 1L;
        AuthorDTO request = new AuthorDTO(id, "Jane", "Doe");

        // When && Then
        assertThatThrownBy(() -> underTest.updateAuthor(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author with id [%s] not found.".formatted(id));

        verify(authorDAO, never()).updateAuthor(any());
    }

    @Test
    void shouldThrowWhenUpdatingWhenNoChangesFound() {
        // Given
        Long id = 1L;
        AuthorDTO request = new AuthorDTO(id, "Jane", "Doe");
        Author existingAuthor = new Author(id, "Jane", "Doe");

        when(authorDAO.findById(id)).thenReturn(Optional.of(existingAuthor));


        // When && Then
        assertThatThrownBy(() -> underTest.updateAuthor(id, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No data changes found.");

        verify(authorDAO, never()).updateAuthor(any());
    }

    @Test
    void shouldThrowWhenAddingAuthorWithExistingFirstNameLastName() {
        // Given
        String firstName = "Jane";
        String lastName = "Doe";
        AuthorDTO request = new AuthorDTO( 1L, firstName, lastName);

        given(authorDAO.existsAuthorByName(firstName,lastName)).willReturn(true);

        // When && Then
        assertThatThrownBy(() -> underTest.addAuthor(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Author with name [%s %s] already exists.".formatted(firstName,lastName));

        verify(authorDAO, never()).addAuthor(any());
    }

}