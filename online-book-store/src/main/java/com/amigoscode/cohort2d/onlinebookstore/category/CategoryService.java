package com.amigoscode.cohort2d.onlinebookstore.category;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<CategoryDTO> getCategories() {
        return CategoryDTOMapper.INSTANCE.modelToDTO(categoryDAO.findAllCategories());
    }
}
