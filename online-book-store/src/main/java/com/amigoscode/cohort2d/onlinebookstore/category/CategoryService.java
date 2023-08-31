package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.RequestValidationException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<CategoryDTO> getCategories() {
        return CategoryDTOMapper.INSTANCE.modelToDTO(categoryDAO.findAllCategories());
    }

    public void addCategory(CategoryDTO categoryDTO) {

        // check if category name exists
        if(categoryDAO.existsCategoryByName(categoryDTO.name())){
            throw new DuplicateResourceException(
                    "Category with name [%s] already exists.".formatted(categoryDTO.name())
            );
        }

        this.categoryDAO.addCategory(CategoryDTOMapper.INSTANCE.dtoToModel(categoryDTO));
    }

    public CategoryDTO getCategoryById(Long id) {
        return CategoryDTOMapper.INSTANCE.modelToDTO(
                categoryDAO.findById(id)
                        .orElseThrow(
                                () -> getResourceNotFoundException(id)));
    }

    public void updateCategory(Long id, CategoryDTO updateRequest) {
        // find category - check exists
        Category existingCategory = categoryDAO.findById(id)
                .orElseThrow(() -> getResourceNotFoundException(id)
                );

        // check if there are any changes
        if(CategoryDTOMapper.INSTANCE.modelToDTO(existingCategory).equals(updateRequest)){
            throw new RequestValidationException("No data changes found.");
        }

        // name
        if(!updateRequest.name().equals(existingCategory.getName())){
            existingCategory.setName(updateRequest.name());
        }

        // description
        if(!updateRequest.description().equals(existingCategory.getDescription())){
            existingCategory.setDescription(updateRequest.description());
        }

        categoryDAO.updateCategory(existingCategory);
    }

    public void deleteCategoryById(Long id) {
        // if category does not exist throw
        checkIfCategoryExistsOrThrow(id);

        //delete
        categoryDAO.deleteCategoryById(id);
    }

    private void checkIfCategoryExistsOrThrow(Long id) {
        if(!categoryDAO.existsCategoryById(id)) {
            throw getResourceNotFoundException(id);
        }
    }

    private static ResourceNotFoundException getResourceNotFoundException(Long id) {
        return new ResourceNotFoundException("Category with id [%s] not found.".formatted(id));
    }
}

