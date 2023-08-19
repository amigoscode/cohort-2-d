package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BookJPAService implements BookDAO {

    private final BookRepository bookRepository;
    private final BookSpecifications bookSpecifications;

    @Override
    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean existsBookByIsbn(String isbn) throws DuplicateResourceException {
        return bookRepository.existsBooksByIsbn(isbn);
    }

    @Override
    public boolean existsBookById(Long id) {
        return bookRepository.existsBookById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Page<BookDTO> searchBooks(String query, Pageable pageable) {

        Specification<Book> spec = Specification
                .where(bookSpecifications.hasTitleLike(query))
                .or(bookSpecifications.hasAuthorNameLike(query));

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);

        return bookPage.map(BookDTOMapper.INSTANCE::modelToDTO);
    }
}
