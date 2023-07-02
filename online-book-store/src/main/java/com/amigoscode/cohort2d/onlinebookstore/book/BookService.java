package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier(value = "jpa")
public class BookService {

    private final BookDAO bookDAO;
    private final BookDTOMapper bookDTOMapper;

    public List<BookDTO> getAllBooks() {
        return bookDTOMapper.modelToDTO(bookDAO.findAllBooks());
    }

    public BookDTO getBookById(int id){
        return bookDTOMapper.modelToDTO(
                bookDAO.findById(id)
                .orElseThrow(
                () -> new ResourceNotFoundException("Book with id [%s] not found".formatted(id))
        ));
    }
}
