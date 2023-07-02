package com.amigoscode.cohort2d.onlinebookstore.book;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookDTOMapperTest {

    private final BookDTOMapper bookDTOMapper = Mappers.getMapper(BookDTOMapper.class);


    @Test
    void shouldTransformModelToDTO() {

        // Given
        BigDecimal price = BigDecimal.valueOf(19.99);
        LocalDate publishDate = LocalDate.of(1954, 7, 29);

        Book model = new Book(
                1L, "1234567890123", "Lord of the Rings", "Fantasy book",
                price, 300, 250, publishDate, BookFormat.DIGITAL
        );

        // When
        BookDTO bookDTO = bookDTOMapper.modelToDTO(model);

        // Then
        assertEquals(bookDTO.id(), model.getId());
        assertEquals(bookDTO.isbn(), model.getIsbn());
        assertEquals(bookDTO.title(), model.getTitle());
        assertEquals(bookDTO.description(), model.getDescription());
        assertEquals(bookDTO.price(), model.getPrice());
        assertEquals(bookDTO.numberOfPages(), model.getNumberOfPages());
        assertEquals(bookDTO.quantity(), model.getQuantity());
        assertEquals(bookDTO.publishDate(), model.getPublishDate());
        assertEquals(bookDTO.bookFormat(), model.getBookFormat().toString());

    }



    @Test
    void shouldTransformDtoToModel() {

        // Given
        BigDecimal price = BigDecimal.valueOf(19.99);
        LocalDate publishDate = LocalDate.of(1954, 7, 29);

        BookDTO bookDTO = new BookDTO(
                1L, "1234567890123", "Lord of the Rings", "Fantasy book",
                price, 300, 250, publishDate, "DIGITAL"
        );

        // When
        Book model = bookDTOMapper.dtoToModel(bookDTO);

        // Then
        assertEquals(bookDTO.id(), model.getId());
        assertEquals(bookDTO.isbn(), model.getIsbn());
        assertEquals(bookDTO.title(), model.getTitle());
        assertEquals(bookDTO.description(), model.getDescription());
        assertEquals(bookDTO.price(), model.getPrice());
        assertEquals(bookDTO.numberOfPages(), model.getNumberOfPages());
        assertEquals(bookDTO.quantity(), model.getQuantity());
        assertEquals(bookDTO.publishDate(), model.getPublishDate());
        assertEquals(bookDTO.bookFormat(), model.getBookFormat().toString());
    }

}