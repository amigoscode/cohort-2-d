package com.amigoscode.cohort2d.onlinebookstore.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorJPAServiceTest {

    private AuthorJPAService underTest;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp(){
        underTest = new AuthorJPAService(authorRepository);
    }

    @Test
    void shouldFindAllAuthors() {
        // Given &&  When
        underTest.findAllAuthors();

        // Then
        verify(authorRepository).findAll();
    }

    @Test
    void shouldCheckAuthorExistsByName() {
        // Given
        String firstName = "Susan";
        String lastName = "Doyle";

        // When
        underTest.existsAuthorByName(firstName, lastName);

        // Then
        verify(authorRepository).existsAuthorByFirstNameAndLastName(firstName,lastName);
    }

    @Test
    void shouldCheckAuthorExistsById() {
        // Given
        Long id = 1L;

        // When
        underTest.existsAuthorById(id);

        // Then
        verify(authorRepository).existsById(id);
    }

    @Test
    void shouldAddAuthor() {
        // Given
        Author author = new Author(1L, "Susan", "Doyle");

        // When
        underTest.addAuthor(author);

        // Then
        verify(authorRepository).save(author);
    }

    @Test
    void shouldGetAuthorById() {
        // Given
        Long id = 10L;

        // When
        underTest.findById(id);

        // Then
        verify(authorRepository).findById(id);

    }

    @Test
    void shouldUpdateAuthor() {
        // Given
        Author author = new Author(1L, "Susan", "Doyle");

        // When
        underTest.updateAuthor(author);

        // Then
        verify(authorRepository).save(author);

    }

    @Test
    void shouldDeleteAuthorById() {
        // Given
        Long id = 1L;

        // When
        underTest.deleteAuthorById(id);

        // Then
        verify(authorRepository).deleteById(id);

    }
}