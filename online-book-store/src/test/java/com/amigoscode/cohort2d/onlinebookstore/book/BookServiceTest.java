package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTO;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorRepository;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTO;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryRepository;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import com.amigoscode.cohort2d.onlinebookstore.service.EntityPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private EntityPersistenceService entityPersistenceService;

    @BeforeEach
    void setUp() {
        underTest = new BookService(bookDAO, categoryRepository, authorRepository, entityPersistenceService);
    }

    @Test
    void shouldGetAllBooks() {

       // Given - more than one book
        String isbn = "12043953321";
        Book request1 = BookDTOMapper.INSTANCE.dtoToModel(getBookDTO(isbn));

        String isbn2 = "12043953322";
        Book request2 = BookDTOMapper.INSTANCE.dtoToModel(getBookDTO(isbn2));

        given(bookDAO.findAllBooks()).willReturn(List.of(request1, request2));

        // when -  action or the behaviour that we are going test
        List<BookDTO> bookList = underTest.getAllBooks();

        // then - verify the output
        assertThat(bookList).isNotNull();
        assertThat(bookList.size()).isEqualTo(2);

    }


    @Test
    void shouldGetBookById() {

        // Given
        String isbn = "12043953321";
        BookDTO request = getBookDTO(isbn);

        given(bookDAO.findById(1)).willReturn(Optional.of(BookDTOMapper.INSTANCE.dtoToModel(request)));

        // When
        BookDTO actual = underTest.getBookById(1);

        // Then
        assertThat(actual).isEqualTo(request);

    }

    @Test
    void itShouldAddBook() {

        // Given
        String isbn = "12043953324";
        BookDTO bookDto = getBookDTO(isbn);
        when(bookDAO.existsBookWithIsbn(isbn)).thenReturn(false);

        // authors
        Set<AuthorDTO> authors = bookDto.authors(); // Get the set of authors from bookDto
        Set<Author> authorEntities = new HashSet<>();
        for (AuthorDTO authorDto : authors) {
            Author authorEntity = new Author();
            authorEntities.add(authorEntity);
        }

        when(entityPersistenceService.getOrCreateEntities(
                anySet(), eq(authorRepository), any())
        ).thenReturn(authorEntities);

        // categories
        Set<CategoryDTO> categories = bookDto.categories(); // Get the set of authors from bookDto
        Set<Category> categoryEntities = new HashSet<>();
        for (CategoryDTO categoryDTO : categories) {
            Category categoryEntity = new Category();
            categoryEntities.add(categoryEntity);
        }


        when(entityPersistenceService.getOrCreateEntities(
                anySet(), eq(categoryRepository), any())
        ).thenReturn(categoryEntities);

        Book addedBook = new Book();
        when(bookDAO.addBook(any(Book.class))).thenReturn(addedBook);

        // When
        underTest.addBook(bookDto);

        // Verify that the methods were called with the expected arguments
        verify(bookDAO).existsBookWithIsbn(isbn);
        verify(bookDAO, times(1)).addBook(any(Book.class));
        verify(entityPersistenceService, times(1)).getOrCreateEntities(
                anySet(), eq(authorRepository), any()
        );
        verify(entityPersistenceService, times(1)).getOrCreateEntities(
                anySet(), eq(categoryRepository), any()
        );

    }

    @Test
    void shouldThrowWhenGetBookReturnEmptyOptional() {
        // Given
        int id = 10;
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
        BookDTO request = getBookDTO(isbn);

        given(bookDAO.existsBookWithIsbn(isbn)).willReturn(true);

        // When && Then
        assertThatThrownBy(() -> underTest.addBook(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Book with ISBN [%s] already exists.".formatted(isbn));

        verify(bookDAO, never()).addBook(any());
    }


    BookDTO getBookDTO(String isbn){

        AuthorDTO author = new AuthorDTO(1L, "Douglas", "Norman");
        Set<AuthorDTO> authors = new HashSet<>();
        authors.add(author);

        CategoryDTO category = new CategoryDTO(1L, "Mystery", "Mystery");
        Set<CategoryDTO> categories = new HashSet<>();
        categories.add(category);

        BookDTO request = new BookDTO(
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

        return request;

    }

}