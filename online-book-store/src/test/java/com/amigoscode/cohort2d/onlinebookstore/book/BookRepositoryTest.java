package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.AbstractTestcontainers;
import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorRepository;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryRepository;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class BookRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private BookRepository underTest;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        underTest.deleteAll();
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfBookExistsByIsbn() {
        // Given
        Long id = 1L;
        String isbn = "1234567891234";

        Book book = getBook(id, isbn);
        underTest.save(book);

        // When
        boolean actual = underTest.existsBooksByIsbn(isbn);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void shouldCheckIfBookExistsById() {
        // Given
        Long id = 1L;
        String isbn = "1234567891234";
        Book book = getBook(id, isbn);
        underTest.save(book);

        // When
        boolean actual = underTest.existsBookById(id);

        // Then
        assertThat(actual).isTrue();
    }

    Book getBook(Long id, String isbn) {
        Author author = authorRepository.save(new Author(1L, "Douglas", "Norman"));
        Set<Author> authors = new HashSet<>();
        authors.add(author);

        Category category = categoryRepository.save(new Category(1L, "Best Sellers", "Mystery"));
        Set<Category> categories = new HashSet<>();
        categories.add(category);

        Book book = new Book(
                1L,
                isbn,
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
        return book;
    }
}