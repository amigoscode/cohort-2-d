package com.amigoscode.cohort2d.onlinebookstore.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books/categories")
public class CategoryController {

    private CategoryService categoryService;

}
