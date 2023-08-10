package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDAO bookDAO;

    public List<BookDTO> getAllBooks() {
        List<Book> allBooks = bookDAO.findAllBooks();
        return BookDTOMapper.INSTANCE.modelToDTO(allBooks);
    }

    public BookDTO getBookById(Long id){
        return BookDTOMapper.INSTANCE.modelToDTO(
                bookDAO.findById(id)
                .orElseThrow(
                () -> new ResourceNotFoundException("Book with id [%s] not found.".formatted(id))
        ));
    }

    public void addBook(BookDTO bookDTO) {

        // check if book isbn exists
        if(bookDAO.existsBookByIsbn(bookDTO.isbn())){
            throw new DuplicateResourceException(
                    "Book with ISBN [%s] already exists.".formatted(bookDTO.isbn())
            );
        }

        //save
        bookDAO.addBook(BookDTOMapper.INSTANCE.dtoToModel(bookDTO));

    }

    public void deleteBookById(Long id) {

        // check if book with id is present
        if(!bookDAO.existsBookById(id)){
            throw new ResourceNotFoundException("Book with id [%s] not found.".formatted(id));
        }

        bookDAO.deleteBookById(id);
    }
}
