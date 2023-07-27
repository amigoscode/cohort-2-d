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
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsAuthorByName(String firstName, String lastName) {
        return authorRepository.existsAuthorByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }
}
