package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTO;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookDTOMapperTest {

    private BookDTOMapper underTest;

    @Mock
    private Book bookModel;

    @Mock
    private BookDTO bookDTO;

    @BeforeEach
    void setUp(){
        underTest = Mappers.getMapper(BookDTOMapper.class);
    }

    @Test
    void shouldTransformModelToDTO() {

        // Given
        Author author = new Author(1L, "Douglas", "Norman");
        Set<Author> authors = new HashSet<>();
        authors.add(author);

        Category category = new Category(1L, "Mystery", "Mystery");
        Set<Category> categories = new HashSet<>();
        categories.add(category);

        bookModel = new Book(
                1L,
                "1234567890123",
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
        BookDTO expected = underTest.modelToDTO(bookModel);

        // Then
        assertEquals(expected.id(), bookModel.getId());
        assertEquals(expected.isbn(), bookModel.getIsbn());
        assertEquals(expected.title(), bookModel.getTitle());
        assertEquals(expected.description(), bookModel.getDescription());
        assertEquals(expected.price(), bookModel.getPrice());
        assertEquals(expected.numberOfPages(), bookModel.getNumberOfPages());
        assertEquals(expected.quantity(), bookModel.getQuantity());
        assertEquals(expected.publishDate(), bookModel.getPublishDate());
        assertEquals(expected.bookFormat(), bookModel.getBookFormat());
        assertEquals(expected.authors().size(), bookModel.getAuthors().size());
        assertEquals(expected.categories().size(), bookModel.getCategories().size());

    }


    @Test
    void shouldTransformDtoToModel() {

        // Given
        AuthorDTO author = new AuthorDTO(1L, "Douglas", "Norman");
        Set<AuthorDTO> authors = new HashSet<>();
        authors.add(author);

        CategoryDTO category = new CategoryDTO(1L, "Mystery", "Mystery");
        Set<CategoryDTO> categories = new HashSet<>();
        categories.add(category);

        bookDTO = new BookDTO(
                1L,
                "1234567890123",
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
        Book expected = underTest.dtoToModel(bookDTO);

        // Then
        assertEquals(bookDTO.id(), expected.getId());
        assertEquals(bookDTO.isbn(), expected.getIsbn());
        assertEquals(bookDTO.title(), expected.getTitle());
        assertEquals(bookDTO.description(), expected.getDescription());
        assertEquals(bookDTO.price(), expected.getPrice());
        assertEquals(bookDTO.numberOfPages(), expected.getNumberOfPages());
        assertEquals(bookDTO.quantity(), expected.getQuantity());
        assertEquals(bookDTO.publishDate(), expected.getPublishDate());
        assertEquals(bookDTO.bookFormat(), expected.getBookFormat());
        assertEquals(bookDTO.authors().size(), expected.getAuthors().size());
        assertEquals(bookDTO.categories().size(), expected.getCategories().size());

    }


}