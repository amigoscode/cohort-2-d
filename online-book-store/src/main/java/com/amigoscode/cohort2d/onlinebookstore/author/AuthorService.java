package com.amigoscode.cohort2d.onlinebookstore.author;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.RequestValidationException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorDAO authorDAO;

    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors =  authorDAO.findAllAuthors();
        return AuthorDTOMapper.INSTANCE.modelToDTO(authors);
    }

    public void addAuthor(AuthorDTO authorDTO) {

        // check if author firstname & lastname combination exists
        if(authorDAO.existsAuthorByName(authorDTO.firstName(), authorDTO.lastName())){
            throw new DuplicateResourceException(
                    "Author with name [%s %s] already exists.".formatted(authorDTO.firstName(), authorDTO.lastName())
            );
        }

        //save
        authorDAO.addAuthor(AuthorDTOMapper.INSTANCE.dtoToModel(authorDTO));
    }

    public AuthorDTO getAuthorById(Long id) {
        return AuthorDTOMapper.INSTANCE.modelToDTO(
                authorDAO.findById(id)
                .orElseThrow(
                        () -> getResourceNotFoundException(id)));
    }

    public void updateAuthor(Long id, AuthorDTO updateRequest) {

        // find book - check exists
        Author existingAuthor = authorDAO.findById(id)
                .orElseThrow(
                        () -> getResourceNotFoundException(id));

        // check if there are any changes
        if(AuthorDTOMapper.INSTANCE.modelToDTO(existingAuthor).equals(updateRequest)){
            throw new RequestValidationException("No data changes found.");
        }

        // first name
        if(!updateRequest.firstName().equals(existingAuthor.getFirstName())){
            existingAuthor.setFirstName(updateRequest.firstName());
        }

        // last name
        if(!updateRequest.lastName().equals(existingAuthor.getLastName())){
            existingAuthor.setLastName(updateRequest.lastName());
        }

        authorDAO.updateAuthor(existingAuthor);

    }

    public void deleteAuthorById(Long id) {

        // if author does not exist throw
        checkIfAuthorExistsOrThrow(id);

        //delete
        authorDAO.deleteAuthorById(id);
    }

    private void checkIfAuthorExistsOrThrow(Long id) {
        if(!authorDAO.existsAuthorById(id)) {
            throw getResourceNotFoundException(id);
        }
    }

    private static ResourceNotFoundException getResourceNotFoundException(Long id) {
        return new ResourceNotFoundException("Author with id [%s] not found.".formatted(id));
    }
}
