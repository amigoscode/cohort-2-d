package com.amigoscode.cohort2d.onlinebookstore.author;

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

        // When
        List<AuthorDTO> authorList = underTest.getAllAuthors();

        // Then
        assertThat(authorList).isNotNull();
        assertThat(authorList.size()).isEqualTo(2);
    }

    @Test
    void shouldAddAuthor() {
        // Given
        AuthorDTO authorDTO = new AuthorDTO(1L, "John", "Doe");
        when(authorDAO.existsAuthorByName("John", "Doe")).thenReturn(false);

        // When
        underTest.addAuthor(authorDTO);

        // Verify that the methods were called with the expected arguments
        verify(authorDAO).existsAuthorByName("John", "Doe");
        verify(authorDAO, times(1)).addAuthor(any(Author.class));

    }


    @Test
    void shouldThrowWhenAddAuthorWithExistingFirstNameLastName() {
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