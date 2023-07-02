package com.amigoscode.cohort2d.onlinebookstore.book;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface BookDTOMapper {

    BookDTOMapper INSTANCE = Mappers.getMapper( BookDTOMapper.class );

    BookDTO modelToDTO(Book book);

    List<BookDTO> modelToDTO(Iterable<Book> books);

    Book dtoToModel(BookDTO bookDTO);

    List<Book> dtoToModel(Iterable<BookDTO> bookDTOS);

}


