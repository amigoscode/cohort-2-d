package com.amigoscode.cohort2d.onlinebookstore.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO  (
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String description
) {

}
