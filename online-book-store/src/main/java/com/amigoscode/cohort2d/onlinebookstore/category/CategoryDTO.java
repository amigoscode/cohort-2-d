package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.service.EntityIdentifiers;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO  (
        Long id,

        @NotNull
        @Column(unique=true)
        String name,

        @NotNull
        String description
) implements EntityIdentifiers {

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName().replace("DTO", "");
    }
}
