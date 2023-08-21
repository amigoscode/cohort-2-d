package com.amigoscode.cohort2d.onlinebookstore.book;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<Page<BookDTO>> getBooks(Pageable pageable) {
        Page<BookDTO> allBooks = bookService.getAllBooks(pageable);
        return ResponseEntity.ok()
                .body(allBooks);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id){
        BookDTO bookById = bookService.getBookById(id);
        return ResponseEntity.ok()
                .body(bookById);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO){
        BookDTO savedBook = bookService.addBook(bookDTO);
        return ResponseEntity.created(URI.create("/api/v1/books/" + savedBook.id()))
                .body(savedBook);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable("id") Long id){
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable("id") Long id,
            @RequestBody BookDTO updateRequest) {
        BookDTO updatedBook = bookService.updateBook(id, updateRequest);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDTO>> searchBooks(BookSearchRequestDTO searchRequest, Pageable pageable){
        Page<BookDTO> bookDTOList = bookService.searchBooks(searchRequest, pageable);
        return ResponseEntity.ok()
                .body(bookDTOList);
    }
}