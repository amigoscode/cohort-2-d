package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTO;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTO;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record BookDTO (
        Long id,

        @NotNull
        String isbn,

        @NotNull
        String title,

        @NotNull
        String description,

        @NotNull
        BigDecimal price,

        @NotNull
        Integer numberOfPages,

        @NotNull
        Integer quantity,

        @NotNull
        LocalDate publishDate,

        @NotNull
        BookFormat bookFormat,

        @NotNull
        Set<AuthorDTO> authors,

        @NotNull
        Set<CategoryDTO>categories

){

}
