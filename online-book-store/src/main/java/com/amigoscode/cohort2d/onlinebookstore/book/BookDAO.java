package com.amigoscode.cohort2d.onlinebookstore.book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {

    List<Book> findAllBooks();

    Optional<Book> findById(Long id);

    Book addBook(Book book);

    boolean existsBookWithIsbn(String isbn);

}
