package com.amigoscode.cohort2d.onlinebookstore.author;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
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
}
