package com.amigoscode.cohort2d.onlinebookstore.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorJPAService implements AuthorDAO {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAllAuthors() {
        return null;
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.empty();
    }
}
