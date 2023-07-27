package com.amigoscode.cohort2d.onlinebookstore.author;

import jakarta.validation.constraints.NotNull;

public record AuthorDTO(
        Long id,

        @NotNull
        String firstName,

        @NotNull
        String lastName
)  {}
