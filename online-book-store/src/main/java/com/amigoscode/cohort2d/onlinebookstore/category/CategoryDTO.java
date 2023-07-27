package com.amigoscode.cohort2d.onlinebookstore.category;

import jakarta.validation.constraints.NotNull;

public record CategoryDTO  (
        Long id,

        @NotNull
        String name,
        @NotNull
        String description
) {

}
