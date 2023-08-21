package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.RequestValidationException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookDAO bookDAO;

    private final int maxPageSize;
    private final int maxQueryLength;
    private final String notFoundMessage;

    public BookService(BookDAO bookDAO,
                       @Value("${spring.search.pageable.max-page-size}") int maxPageSize,
                       @Value("${spring.search.max-query-length}") int maxQueryLength) {
        this.bookDAO = bookDAO;
        this.maxPageSize = maxPageSize;
        this.maxQueryLength = maxQueryLength;
        this.notFoundMessage = "Book with id [%s] not found.";
    }

    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> allBooks = bookDAO.findAllBooks(pageable);
        return allBooks.map(BookDTOMapper.INSTANCE::modelToDTO);
    }

    public BookDTO getBookById(Long id){
        return BookDTOMapper.INSTANCE.modelToDTO(
                bookDAO.findById(id)
                .orElseThrow(
                () -> new ResourceNotFoundException(notFoundMessage.formatted(id))
        ));
    }

    public BookDTO addBook(BookDTO request) {

        // check if book isbn exists
        if(bookDAO.existsBookByIsbn(request.isbn())){
            throw new DuplicateResourceException(
                    "Book with ISBN [%s] already exists.".formatted(request.isbn())
            );
        }

        //save
        bookDAO.addBook(BookDTOMapper.INSTANCE.dtoToModel(request));

        return request;
    }

  public void deleteBookById(Long id) {

        // check if book with id is present
        if(!bookDAO.existsBookById(id)){
            throw new ResourceNotFoundException(notFoundMessage.formatted(id));
        }

        bookDAO.deleteBookById(id);
  }

  public BookDTO updateBook(Long id, BookDTO updateRequest) {

        Book existingBook = bookDAO.findById(updateRequest.id())
            .orElseThrow(() -> new ResourceNotFoundException(
                    notFoundMessage.formatted(id)
            ));


        if (BookDTOMapper.INSTANCE.modelToDTO(existingBook).equals(updateRequest)) { // Assuming you have an appropriate equals method
            throw new RequestValidationException("No data changes found.");
        }

        // isbn
        if (!updateRequest.isbn().equals(existingBook.getIsbn())) {
            if (bookDAO.existsBookByIsbn(updateRequest.isbn())) {
                throw new DuplicateResourceException(
                        "Book with ISBN [%s] already exists.".formatted(updateRequest.isbn())
                );
            }
            existingBook.setIsbn(updateRequest.isbn());
        }

        // title
        if (!updateRequest.title().equals(existingBook.getTitle())) {
            existingBook.setTitle(updateRequest.title());
        }

        // description;
        if (!updateRequest.description().equals(existingBook.getDescription())) {
            existingBook.setDescription(updateRequest.description());
        }

        // price;
        if (!updateRequest.price().equals(existingBook.getPrice())) {
            existingBook.setPrice(updateRequest.price());
        }

        // numberOfPages;
        if (!updateRequest.numberOfPages().equals(existingBook.getNumberOfPages())) {
            existingBook.setNumberOfPages(updateRequest.numberOfPages());
        }

        // quantity;
        if (!updateRequest.quantity().equals(existingBook.getQuantity())) {
            existingBook.setQuantity(updateRequest.quantity());
        }

        // publishDate;
        if (!updateRequest.publishDate().equals(existingBook.getPublishDate())) {
            existingBook.setPublishDate(updateRequest.publishDate());
        }

        // bookFormat
        if (!updateRequest.bookFormat().equals(existingBook.getBookFormat())) {
            existingBook.setBookFormat(updateRequest.bookFormat());
        }

        // authors
        List<Author> updatedAuthors = AuthorDTOMapper.INSTANCE.dtoToModel(updateRequest.authors());
        if (!updatedAuthors.equals(existingBook.getAuthors())) {
            existingBook.setAuthors(updatedAuthors);
        }

        // categories
        List<Category> updatedCategories = CategoryDTOMapper.INSTANCE.dtoToModel(updateRequest.categories());
        if (!updatedCategories.equals(existingBook.getCategories())) {
            existingBook.setCategories(updatedCategories);
        }


        bookDAO.updateBook(existingBook);
      return updateRequest;
  }

    public Page<BookDTO> searchBooks(BookSearchRequestDTO searchRequest, Pageable pageable) {

        String query = searchRequest.query();

        if(query == null || pageable == null){
            throw new RequestValidationException("Query or page must not be null.");
        }

        if(query.length() > maxQueryLength) {
            throw new RequestValidationException("Search is too long, max [%s] characters.".formatted(maxQueryLength));
        }

        if(query.isBlank()){
            throw new RequestValidationException("Search must not be blank.");
        }

        if (pageable.getPageSize() > maxPageSize) {
            throw new RequestValidationException("Page size is too large. Maximum allowed is [%s].".formatted(maxPageSize));
        }

        return bookDAO.searchBooks(query, pageable);
    }
}
