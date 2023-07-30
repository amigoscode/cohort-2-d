package com.amigoscode.cohort2d.onlinebookstore.author;

import jakarta.validation.constraints.NotBlank;

public record AuthorDTO(
        Long id,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName
)  {}
