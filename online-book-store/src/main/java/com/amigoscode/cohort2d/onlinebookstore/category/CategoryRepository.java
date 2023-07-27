package com.amigoscode.cohort2d.onlinebookstore.category;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsCategoryByName(String name);

}
