package com.amigoscode.cohort2d.onlinebookstore.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookJPAServiceTest {

    private BookJPAService underTest;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        underTest = new BookJPAService(bookRepository);
    }

    @Test
    void shouldFindAllBooks() {

        // Given && When
        underTest.findAllBooks();

        // Then
        verify(bookRepository).findAll();
    }

    @Test
    void shouldFindById() {

        // Given
        int id = 1;

        // When
        underTest.findById(id);

        // Then
        verify(bookRepository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenExistsBookWithIsbn() {

        // Given
        String isbn = "13043953922";

        // When
        underTest.existsBookWithIsbn(isbn);

        // Then
        verify(bookRepository).existsBooksByIsbn(isbn);
    }

}