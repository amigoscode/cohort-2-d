package com.amigoscode.cohort2d.onlinebookstore.book;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private BookDTO getBookById(@PathVariable("id") int id){
        return bookService.getBookById(id);
    }
}