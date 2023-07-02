package com.amigoscode.cohort2d.onlinebookstore.book;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;


class BookJPAServiceTest {

    private BookJPAService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void initUseCase() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new BookJPAService(bookRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
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

}