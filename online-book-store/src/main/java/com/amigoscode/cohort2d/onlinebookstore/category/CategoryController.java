package com.amigoscode.cohort2d.onlinebookstore.category;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDTO> getCategories() {

        return categoryService.getCategories();
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO);

        return ResponseEntity.ok()
                .build();

    }
}

