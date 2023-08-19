package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTO;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookJPAServiceTest {

    private BookJPAService underTest;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookSpecifications bookSpecifications;

    @BeforeEach
    void setUp() {
        underTest = new BookJPAService(bookRepository, bookSpecifications);
    }

    @Test
    void shouldFindAllBooks() {

        Pageable pageable = Pageable.ofSize(2);

        // Given && When
        underTest.findAllBooks(pageable);

        // Then
        verify(bookRepository).findAll(pageable);
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
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        Category category = new Category(1L, "Best Sellers", "Mystery");
        List<Category> categories = new ArrayList<>();
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
    void shouldUpdateBook(){
        // Given
        Author author = new Author(1L, "Douglas", "Norman");
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        Category category = new Category(1L, "Best Sellers", "Mystery");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Book book = new Book(
                1L,
                "1234567891234",
                "",
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
        underTest.updateBook(book);

        // Then
        verify(bookRepository).save(book);
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

    @Test
    void shouldSearchBooks() {
        // Given
        String query = "hobbit";
        Pageable pageable = PageRequest.of(0, 10);
        Book book1 = getBook(1L, "1234567890123", "Book 1"); // populate with necessary data
        Book book2 = getBook(2L, "2234567890123", "Hobbit Book"); // populate with necessary data
        Page<Book> mockPage = new PageImpl<>(List.of(book1, book2));

        given(bookRepository.findAll(any(Specification.class), eq(pageable))).willReturn(mockPage);

        // When
        Page<BookDTO> resultPage = underTest.searchBooks(query, pageable);

        // Then
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);

    }

    Book getBook(Long id, String isbn, String title){

        Author author = new Author(1L, "Douglas", "Norman");
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        Category category = new Category(1L, "Mystery", "Mystery");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        return new Book(
                id,
                isbn,
                title,
                "The best book",
                BigDecimal.valueOf(19.99),
                300,
                250,
                LocalDate.of(1954, 7, 29),
                BookFormat.DIGITAL,
                authors,
                categories
        );

    }
}