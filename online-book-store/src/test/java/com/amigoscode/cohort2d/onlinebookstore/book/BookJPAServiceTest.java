package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
        Long id = 1L;

        // When
        underTest.findById(id);

        // Then
        verify(bookRepository).findById(id);
    }

    @Test
    void shouldAddBook(){
        // Given
        Author author = new Author(1L, "Douglas", "Norman");
        Set<Author> authors = new HashSet<>();
        authors.add(author);

        Category category = new Category(1L, "Best Sellers", "Mystery");
        Set<Category> categories = new HashSet<>();
        categories.add(category);

        Book book = new Book(
                1L,
                "1234567891234",
                "Lord of the Rings",
                "Fantasy book",
                BigDecimal.valueOf(19.99),
                300,
                250,
                LocalDate.of(1954, 7, 29),
                BookFormat.DIGITAL,
                authors,
                categories
        );

        // When
        underTest.addBook(book);

        // Then
        verify(bookRepository).save(book);
    }

    @Test
    void shouldDeleteBook() {
        // Given
        Long id = 1L;

        // When
        underTest.deleteBookById(id);

        // Then
        verify(bookRepository).deleteById(id);

    }

    @Test
    void checksExistsBookWithId() {
        // Given
        Long id = 1L;

        // When
        underTest.existsBookById(id);

        // Then
        verify(bookRepository).existsBookById(id);

    }

    @Test
    void checksExistsBookWithIsbn() {

        // Given
        String isbn = "13043953922";

        // When
        underTest.existsBookByIsbn(isbn);

        // Then
        verify(bookRepository).existsBooksByIsbn(isbn);
    }



}