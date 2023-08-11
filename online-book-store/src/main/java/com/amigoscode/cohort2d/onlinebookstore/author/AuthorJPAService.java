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
        return authorRepository.findById(id);
    }

    @Override
    public boolean existsAuthorByName(String firstName, String lastName) {
        return authorRepository.existsAuthorByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public boolean existsAuthorById(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
