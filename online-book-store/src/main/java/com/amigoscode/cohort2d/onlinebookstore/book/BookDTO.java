package com.amigoscode.cohort2d.onlinebookstore.book;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookDTO (
        Long id,
        String isbn,
        String title,
        String description,
        BigDecimal price,
        Integer numberOfPages,
        Integer quantity,
        LocalDate publishDate,
        String bookFormat
){

}
