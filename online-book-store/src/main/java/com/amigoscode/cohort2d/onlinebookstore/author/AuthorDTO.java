package com.amigoscode.cohort2d.onlinebookstore.author;

import com.amigoscode.cohort2d.onlinebookstore.service.EntityIdentifiers;

public record AuthorDTO(
        Long id,
        String firstName,
        String lastName
) implements EntityIdentifiers {

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.firstName +" "+this.lastName;
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName().replace("DTO", "");
    }
}
