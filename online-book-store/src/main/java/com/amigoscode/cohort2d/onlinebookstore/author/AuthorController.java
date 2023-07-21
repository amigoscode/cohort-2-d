package com.amigoscode.cohort2d.onlinebookstore.author;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books/authors")
public class AuthorController {

    private AuthorService categoryService;

}
