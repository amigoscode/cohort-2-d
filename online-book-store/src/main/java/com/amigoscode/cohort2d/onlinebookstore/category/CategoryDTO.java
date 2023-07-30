package com.amigoscode.cohort2d.onlinebookstore.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO  (
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String description
) {

}
