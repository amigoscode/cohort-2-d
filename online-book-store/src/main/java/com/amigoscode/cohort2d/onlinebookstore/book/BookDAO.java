package com.amigoscode.cohort2d.onlinebookstore.book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {

    List<Book> findAllBooks();

    Optional<Book> findById(int id);
}
