package com.amigoscode.cohort2d.onlinebookstore.author;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    private AuthorService underTest;
    private Validator validator;

    @Mock
    private AuthorDAO authorDAO;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
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