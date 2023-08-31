package com.amigoscode.cohort2d.onlinebookstore.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookDAO {

    Page<Book> findAllBooks(Pageable pageable);

    Optional<Book> findById(Long id);

    Book addBook(Book book);

    boolean existsBookByIsbn(String isbn);

    boolean existsBookById(Long id);

    void deleteBookById(Long id);

    Book updateBook(Book book);

    Page<BookDTO> searchBooks(String query, Pageable pageable);
}
