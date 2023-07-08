package com.amigoscode.cohort2d.onlinebookstore.category;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel="spring"
)
public interface CategoryDTOMapper {

    CategoryDTO modelToDTO(Category category);

    List<CategoryDTO> modelToDTO(Iterable<Category> categories);

    Category dtoToModel(CategoryDTO categoryDTO);

    List<Category> dtoToModel(Iterable<CategoryDTO> categoryDTOs);

}
