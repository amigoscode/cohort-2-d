package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
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

    public void addCategory(CategoryDTO categoryDTO) {

        // check if book isbn exists
        if(categoryDAO.existsCategoryByName(categoryDTO.name())){
            throw new DuplicateResourceException(
                    "Category with name [%s] already exists.".formatted(categoryDTO.name())
            );
        }

        this.categoryDAO.addCategory(CategoryDTOMapper.INSTANCE.dtoToModel(categoryDTO));
    }
}
