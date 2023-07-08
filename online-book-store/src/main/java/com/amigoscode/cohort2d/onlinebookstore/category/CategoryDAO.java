package com.amigoscode.cohort2d.onlinebookstore.category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {

    List<Category> findAllCategories();

    Optional<Category> findById(long id);
}
