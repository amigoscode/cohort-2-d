package com.amigoscode.cohort2d.onlinebookstore.book;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public List<BookDTO> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("{id}")
    public BookDTO getBookById(@PathVariable("id") Long id){
        return bookService.getBookById(id);
    }

    @PostMapping
    public ResponseEntity<?> addBook(@Valid
            @RequestBody BookDTO bookDTO){
        bookService.addBook(bookDTO);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("{id}")
    public void deleteBookById(@PathVariable("id") Long id){
        bookService.deleteBookById(id);
    }
}