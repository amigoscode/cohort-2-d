package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorRepository;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryRepository;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import com.amigoscode.cohort2d.onlinebookstore.service.EntityPersistenceService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookService {
    private final BookDAO bookDAO;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final EntityPersistenceService entityPersistenceService;


    public BookService(BookDAO bookDAO,
                       CategoryRepository categoryRepository,
                       AuthorRepository authorRepository,
                       EntityPersistenceService entityPersistenceService) {
        this.bookDAO = bookDAO;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.entityPersistenceService = entityPersistenceService;
    }

    public List<BookDTO> getAllBooks() {

        List<Book> allBooks = bookDAO.findAllBooks();
        return BookDTOMapper.INSTANCE.modelToDTO(allBooks);
    }

    public BookDTO getBookById(int id){
        return BookDTOMapper.INSTANCE.modelToDTO(
                bookDAO.findById(id)
                .orElseThrow(
                () -> new ResourceNotFoundException("Book with id [%s] not found.".formatted(id))
        ));
    }

    @Transactional
    public void addBook(BookDTO bookDTO) {

        // check if book isbn exists
        if(bookDAO.existsBookWithIsbn(bookDTO.isbn())){
            throw new DuplicateResourceException(
                    "Book with ISBN [%s] already exists.".formatted(bookDTO.isbn())
            );
        }

        Book book = BookDTOMapper.INSTANCE.dtoToModel(bookDTO);

        // get/set category(s) & author(s)
        Set<Category> categories = entityPersistenceService.getOrCreateEntities(
                book.getCategories(),
                categoryRepository);
        Set<Author> authors = entityPersistenceService.getOrCreateEntities(
                book.getAuthors(),
                authorRepository);

        book.setAuthors(authors);
        book.setCategories(categories);


        for (Author author : authors) {
            author.addBook(book);
        }
        for (Category category : categories) {
                category.addBook(book);
        }


        //save

        bookDAO.addBook(book);



    }

}
