package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CategoryJPAService implements CategoryDAO {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public Optional<Category> findById(long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsCategoryByName(String name) throws DuplicateResourceException {
        return categoryRepository.existsCategoryByName(name);
    }
}
