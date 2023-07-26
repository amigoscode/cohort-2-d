package com.amigoscode.cohort2d.onlinebookstore.service;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTO;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorRepository;
import com.amigoscode.cohort2d.onlinebookstore.book.Book;
import com.amigoscode.cohort2d.onlinebookstore.book.BookDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntityPersistenceServiceTest {


    private EntityPersistenceService underTest;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        underTest = new EntityPersistenceService();
    }

    @Test
    void shouldGetAuthorEntities() {
        // given
        Set<Author> authors = new HashSet<>();
        Author author = new Author(1L, "FirstName", "LastName");
        authors.add(author);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        // when
        Set<Author> result = underTest.getOrCreateEntities(authors, authorRepository);

        // then
        assertEquals(1, result.size());
        assertTrue(result.contains(author));

        verify(authorRepository).findById(1L);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void shouldCreateAuthorEntities() {
        // given
        Set<Author> authors = new HashSet<>();
        Author author = new Author(null, "FirstName", "LastName");
        authors.add(author);

        Author expectedAuthor = new Author(1L, "FirstName", "LastName");
        when(authorRepository.save(any())).thenReturn(expectedAuthor);

        // when
        Set<Author> actual = underTest.getOrCreateEntities(authors, authorRepository);

        // then
        assertEquals(1, actual.size());
        assertTrue(actual.contains(expectedAuthor));

        verify(authorRepository, times(1)).save(any(Author.class));
    }


}
