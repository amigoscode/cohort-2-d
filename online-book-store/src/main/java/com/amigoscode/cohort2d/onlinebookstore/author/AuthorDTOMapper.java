package com.amigoscode.cohort2d.onlinebookstore.author;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel="spring"
)
public interface AuthorDTOMapper {

    AuthorDTOMapper INSTANCE = Mappers.getMapper(AuthorDTOMapper.class);

    AuthorDTO modelToDTO(Author author);

    List<AuthorDTO> modelToDTO(Iterable<Author> authors);

    Author dtoToModel(AuthorDTO authorDTO);

    List<Author> dtoToModel(Iterable<AuthorDTO> authorDTOs);

}
