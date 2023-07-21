package com.amigoscode.cohort2d.onlinebookstore.service;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTO;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorRepository;
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
        Set<AuthorDTO> dtos = new HashSet<>();
        AuthorDTO dto = new AuthorDTO(1L, "FirstName", "LastName");
        dtos.add(dto);

        Author expectedAuthor = new Author();
        when(authorRepository.findById(1L)).thenReturn(Optional.of(expectedAuthor));

        // when
        Set<Author> result = underTest.getOrCreateEntities(dtos, authorRepository, AuthorDTOMapper.INSTANCE::dtoToModel);

        // then
        assertEquals(1, result.size());
        assertTrue(result.contains(expectedAuthor));

        verify(authorRepository).findById(1L);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void shouldCreateAuthorEntities() {
        // given
        Set<AuthorDTO> dtos = new HashSet<>();
        AuthorDTO dto = new AuthorDTO(null, "FirstName", "LastName");
        dtos.add(dto);

        Author expectedAuthor = new Author();
        when(authorRepository.save(any())).thenReturn(expectedAuthor);

        // when
        Set<Author> actual = underTest.getOrCreateEntities(dtos, authorRepository, AuthorDTOMapper.INSTANCE::dtoToModel);

        // then
        assertEquals(1, actual.size());
        assertTrue(actual.contains(expectedAuthor));

        verify(authorRepository, times(1)).save(any(Author.class));
    }


}
