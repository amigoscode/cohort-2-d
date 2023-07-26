package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.service.EntityIdentifiers;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO  (
        Long id,

        @NotNull
        String name,
        @NotNull
        String description
) {


}
