package com.amigoscode.cohort2d.onlinebookstore.book;


import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface BookDTOMapper {

    BookDTOMapper INSTANCE = Mappers.getMapper(BookDTOMapper.class);


    BookDTO modelToDTO(Book book);

    List<BookDTO> modelToDTO(Iterable<Book> books);

    Book dtoToModel(BookDTO bookDTO);

    List<Book> dtoToModel(Iterable<BookDTO> bookDTOS);

    Author dtoToModel(AuthorDTO authorDTO);
}


