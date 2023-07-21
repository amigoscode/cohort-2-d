package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@RequiredArgsConstructor
@Repository
public class BookJPAService implements BookDAO {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean existsBookWithIsbn(String isbn) throws DuplicateResourceException {
        return bookRepository.existsBooksByIsbn(isbn);
    }


}
