package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.AuthorRepository;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryRepository;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookService {
    private final BookDAO bookDAO;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;


    public BookService(BookDAO bookDAO,
                       CategoryRepository categoryRepository,
                       AuthorRepository authorRepository) {
        this.bookDAO = bookDAO;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

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
        if(bookDAO.existsBookWithIsbn(bookDTO.isbn())){
            throw new DuplicateResourceException(
                    "Book with ISBN [%s] already exists.".formatted(bookDTO.isbn())
            );
        }

        //save
        bookDAO.addBook(BookDTOMapper.INSTANCE.dtoToModel(bookDTO));

    }

}
