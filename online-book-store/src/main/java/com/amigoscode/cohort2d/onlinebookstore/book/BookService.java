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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional /////
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

    public void addBook(BookDTO bookDTO) {

        // check if book isbn exists
        if(bookDAO.existsBookWithIsbn(bookDTO.isbn())){
            throw new DuplicateResourceException(
                    "Book with ISBN [%s] already exists.".formatted(bookDTO.isbn())
            );
        }

        Book book = new Book();

        // get/set category(s) & author(s)
        Set<Category> categories = entityPersistenceService.getOrCreateEntities(
                bookDTO.categories(),
                categoryRepository,
                CategoryDTOMapper.INSTANCE::dtoToModel);
        Set<Author> authors = entityPersistenceService.getOrCreateEntities(
                bookDTO.authors(),
                authorRepository,
                AuthorDTOMapper.INSTANCE::dtoToModel);

        book.setIsbn(bookDTO.isbn());
        book.setTitle(bookDTO.title());
        book.setDescription(bookDTO.description());
        book.setPrice(bookDTO.price());
        book.setNumberOfPages(bookDTO.numberOfPages());
        book.setQuantity(bookDTO.quantity());
        book.setPublishDate(bookDTO.publishDate());
        book.setBookFormat(bookDTO.bookFormat());
        book.setAuthors(authors);
        book.setCategories(categories);

        //save
        BookDTOMapper.INSTANCE.modelToDTO(bookDAO.addBook(book));

    }

}
