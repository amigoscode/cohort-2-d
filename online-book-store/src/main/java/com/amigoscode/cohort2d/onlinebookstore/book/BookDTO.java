package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTO;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookDTO (
        Long id,

        @NotBlank
        @Size(min = 13, max = 13)
        String isbn,

        @NotBlank
        String title,

        @NotBlank
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

        @NotEmpty
        List<AuthorDTO> authors,

        @NotEmpty
        List<CategoryDTO>categories

){

}
