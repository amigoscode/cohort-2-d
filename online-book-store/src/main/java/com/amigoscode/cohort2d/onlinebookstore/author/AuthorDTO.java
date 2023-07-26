package com.amigoscode.cohort2d.onlinebookstore.author;

import com.amigoscode.cohort2d.onlinebookstore.service.EntityIdentifiers;

public record AuthorDTO(
        Long id,
        String firstName,
        String lastName
)  {

}
