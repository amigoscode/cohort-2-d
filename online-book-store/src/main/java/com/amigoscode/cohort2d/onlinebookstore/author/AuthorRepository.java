package com.amigoscode.cohort2d.onlinebookstore.author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsAuthorByFirstNameAndLastName(String firstName, String lastName);
}
