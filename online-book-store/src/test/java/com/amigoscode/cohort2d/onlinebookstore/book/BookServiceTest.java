package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTO;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorRepository;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTO;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryRepository;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private BookService underTest;

    @Mock
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        underTest = new BookService(bookDAO);
    }

    @Test
    void shouldGetAllBooks() {

       // Given - more than one book
        String isbn = "12043953321";
        Book request1 = BookDTOMapper.INSTANCE.dtoToModel(getBookDTO(isbn, 1L));

        String isbn2 = "12043953322";
        Book request2 = BookDTOMapper.INSTANCE.dtoToModel(getBookDTO(isbn2, 2L));

        given(bookDAO.findAllBooks()).willReturn(List.of(request1, request2));

        List<BookDTO> expected = BookDTOMapper.INSTANCE.modelToDTO(bookDAO.findAllBooks());

        // When -  action or the behaviour that we are going test
        List<BookDTO> actual = underTest.getAllBooks();

        // Then - verify the output
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void shouldGetBookById() {

        // Given
        String isbn = "12043953321";
        Long id = 1L;
        BookDTO request = getBookDTO(isbn, id);

        given(bookDAO.findById(id)).willReturn(Optional.of(BookDTOMapper.INSTANCE.dtoToModel(request)));

        // When
        BookDTO actual = underTest.getBookById(id);

        // Then
        assertThat(actual).isEqualTo(request);

    }

    @Test
    void shouldAddBook() {

        // Given
        String isbn = "12043953324";
        BookDTO request = getBookDTO(isbn, null);
        given(bookDAO.existsBookByIsbn(isbn)).willReturn(false);

        // When
        underTest.addBook(request);

        // Then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookDAO).addBook(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook.getId()).isNull();
        assertThat(capturedBook.getTitle()).isEqualTo(request.title());
        assertThat(capturedBook.getDescription()).isEqualTo(request.description());
        assertThat(capturedBook.getPrice()).isEqualTo(request.price());
        assertThat(capturedBook.getQuantity()).isEqualTo(request.quantity());
        assertThat(capturedBook.getNumberOfPages()).isEqualTo(request.numberOfPages());
        assertThat(capturedBook.getPublishDate()).isEqualTo(request.publishDate());
        assertThat(capturedBook.getBookFormat()).isEqualTo(request.bookFormat());

    }

    @Test
    void shouldDeleteBookById() {
        // Given
        Long id = 1L;
        given(bookDAO.existsBookById(id)).willReturn(true);

        // When
        underTest.deleteBookById(id);

        // Then
        verify(bookDAO).deleteBookById(id);
    }

    @Test
    void shouldThrowIfBookNotFoundWhenDeleting() {
        // Given
        Long id = 10L;

        // When && Then
        assertThatThrownBy(() -> underTest.deleteBookById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book with id [%s] not found.".formatted(id));
        verify(bookDAO, never()).deleteBookById(id);
    }



    @Test
    void shouldThrowWhenGetBookReturnEmptyOptional() {
        // Given
        Long id = 10L;
        given(bookDAO.findById(id)).willReturn(Optional.empty());

        // When && Then
        assertThatThrownBy(() -> underTest.getBookById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book with id [%s] not found.".formatted(id));
    }

    @Test
    void shouldThrowWhenAddBookWithExistingIsbn() {
        // Given
        String isbn = "12043953321";
        BookDTO request = getBookDTO(isbn, 1L);

        given(bookDAO.existsBookByIsbn(isbn)).willReturn(true);

        // When && Then
        assertThatThrownBy(() -> underTest.addBook(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Book with ISBN [%s] already exists.".formatted(isbn));

        verify(bookDAO, never()).addBook(any());
    }


    BookDTO getBookDTO(String isbn, Long id){

        AuthorDTO author = new AuthorDTO(1L, "Douglas", "Norman");
        Set<AuthorDTO> authors = new HashSet<>();
        authors.add(author);

        CategoryDTO category = new CategoryDTO(1L, "Mystery", "Mystery");
        Set<CategoryDTO> categories = new HashSet<>();
        categories.add(category);

        BookDTO request = new BookDTO(
                id,
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

        return request;

    }

}