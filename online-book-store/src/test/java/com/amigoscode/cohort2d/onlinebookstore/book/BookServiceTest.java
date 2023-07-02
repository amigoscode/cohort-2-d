package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {


    private AutoCloseable autoCloseable;

    @Mock
    private BookDAO bookDAO;

    @Mock
    private BookDTOMapper bookDTOMapper;

    @InjectMocks
    private BookService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new BookService(bookDAO, bookDTOMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldGetAllBooks() {

        // Given - more than one book
        BigDecimal price = BigDecimal.valueOf(19.99);
        LocalDate publishDate = LocalDate.of(1954, 7, 29);

        BookDTO book1 = new BookDTO(
                1L, "1234567890123", "Lord of the Rings", "Fantasy book",
                price, 300, 250, publishDate, "DIGITAL"
        );

        BookDTO book2 = new BookDTO(
                1L, "1234567890123", "Lord of the Rings", "Fantasy book",
                price, 300, 250, publishDate, "DIGITAL"
        );

        given(bookDTOMapper.modelToDTO(bookDAO.findAllBooks())).willReturn(List.of(book1, book2));

        // when -  action or the behaviour that we are going test
        List<BookDTO> bookList = underTest.getAllBooks();

        // then - verify the output
        assertThat(bookList).isNotNull();
        assertThat(bookList.size()).isEqualTo(2);


    }

    @Test
    void shouldGetBookById() {

        // Given
        BigDecimal price = BigDecimal.valueOf(19.99);
        LocalDate publishDate = LocalDate.of(1954, 7, 29);

        Book model = new Book(
                1L, "1234567890123", "Lord of the Rings", "Fantasy book",
                price, 300, 250, publishDate, BookFormat.DIGITAL
        );
        given(bookDAO.findById(1)).willReturn(Optional.of(model));

        // When
        BookDTO actual = underTest.getBookById(1);

        // Then
        assertThat(actual).isEqualTo(bookDTOMapper.modelToDTO(model));

    }

    @Test
    void shouldThrowWhenGetBookReturnEmptyOptional() {
        // Given
        int id = 10;
        given(bookDAO.findById(id)).willReturn(Optional.empty());

        // When && Then
        assertThatThrownBy(() -> underTest.getBookById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book with id [%s] not found".formatted(id));

    }

}