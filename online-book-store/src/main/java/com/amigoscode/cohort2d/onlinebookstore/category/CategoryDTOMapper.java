package com.amigoscode.cohort2d.onlinebookstore.category;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel="spring"
)
public interface CategoryDTOMapper {

    CategoryDTOMapper INSTANCE = Mappers.getMapper(CategoryDTOMapper.class);

    CategoryDTO modelToDTO(Category category);

    List<CategoryDTO> modelToDTO(Iterable<Category> categories);

    Category dtoToModel(CategoryDTO categoryDTO);

    List<Category> dtoToModel(Iterable<CategoryDTO> categoryDTOs);

}
