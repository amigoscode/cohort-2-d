package com.amigoscode.cohort2d.onlinebookstore.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsBooksByIsbn(String isbn);
}
