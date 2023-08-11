package com.amigoscode.cohort2d.onlinebookstore.author;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDTO> getAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("{id}")
    public AuthorDTO getAuthorById(@PathVariable("id") Long id){
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public ResponseEntity<?> addAuthor(@Valid @RequestBody AuthorDTO authorDTO){
        authorService.addAuthor(authorDTO);

        return ResponseEntity.ok()
                .build();
    }


    @PutMapping("{id}")
    public ResponseEntity<?> updateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDTO authorDTO
    ){
        authorService.updateAuthor(id, authorDTO);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("{id}")
    public void deleteAuthorById(@PathVariable("id") Long id){
        authorService.deleteAuthorById(id);
    }
}
