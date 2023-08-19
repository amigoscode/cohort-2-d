package com.amigoscode.cohort2d.onlinebookstore.book;

import jakarta.validation.constraints.NotBlank;

public record BookSearchRequestDTO (

        // TODO - add category filters and other fields
        @NotBlank
        String query
){
}
