package com.amigoscode.cohort2d.onlinebookstore.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsCategoryByName(String name);

    @Query(
            value = "SELECT * FROM category",
            nativeQuery = true)
    List<Category> getAllCategories();
}
