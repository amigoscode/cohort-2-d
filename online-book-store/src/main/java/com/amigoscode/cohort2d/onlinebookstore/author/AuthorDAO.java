package com.amigoscode.cohort2d.onlinebookstore.author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {

    List<Author> findAllAuthors();

    Optional<Author> findById(long id);

    boolean existsAuthorByName(String s, String s1);

    void addAuthor(Author author);
}
