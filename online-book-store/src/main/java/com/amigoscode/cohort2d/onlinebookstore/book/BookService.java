package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.RequestValidationException;
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

    public void addBook(BookDTO request) {

        // check if book isbn exists
        if(bookDAO.existsBookWithIsbn(request.isbn())){
            throw new DuplicateResourceException(
                    "Book with ISBN [%s] already exists.".formatted(request.isbn())
            );
        }

        //save
        bookDAO.addBook(BookDTOMapper.INSTANCE.dtoToModel(request));

    }


    public void updateBook(Long id, BookDTO updateRequest) {

        Book existingBook = bookDAO.findById(updateRequest.id())
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Book with id [%s] not found.".formatted(id)
            ));


        if (BookDTOMapper.INSTANCE.modelToDTO(existingBook).equals(updateRequest)) { // Assuming you have an appropriate equals method
            throw new RequestValidationException("No data changes found.");
        }

        // isbn
        if (!updateRequest.isbn().equals(existingBook.getIsbn())) {
            if (bookDAO.existsBookWithIsbn(updateRequest.isbn())) {
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
    }
}
