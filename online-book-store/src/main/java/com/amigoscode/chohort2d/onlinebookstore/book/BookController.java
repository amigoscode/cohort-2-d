package com.amigoscode.chohort2d.onlinebookstore.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @GetMapping
    public String testApp() {
        return "App running - Book Controller!";
    }
}
