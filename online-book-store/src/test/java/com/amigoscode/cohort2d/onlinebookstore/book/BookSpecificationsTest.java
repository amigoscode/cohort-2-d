package com.amigoscode.cohort2d.onlinebookstore.book;

import static org.mockito.Mockito.*;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BookSpecificationsTest {


    private BookSpecifications underTest;

    @Mock
    private Root<Book> root;

    @Mock
    private CriteriaQuery<?> criteriaQuery;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private Join<Book, Author> join;

    @BeforeEach
    public void setUp() {
        underTest = new BookSpecifications();
    }

    @Test
    public void shouldCreateTitleLikeSpecification() {
        String query = "hobbit";
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.like(root.get("title"), "%" + query + "%")).thenReturn(mockPredicate);

        Predicate result = underTest.hasTitleLike(query).toPredicate(root, criteriaQuery, criteriaBuilder);

        assertThat(result).isEqualTo(mockPredicate);
    }

    @Test
    public void shouldCreateAuthorNameLikeSpecification() {
        String query = "Tolkien";

        when(root.<Book, Author>join("authors")).thenReturn(join);

        Predicate mockFirstNamePredicate = mock(Predicate.class);
        Predicate mockLastNamePredicate = mock(Predicate.class);
        Predicate mockOrPredicate = mock(Predicate.class);

        when(criteriaBuilder.like(
                criteriaBuilder.lower(
                        join.get("firstName")), "%" + query.toLowerCase() + "%")).thenReturn(mockFirstNamePredicate);
        when(criteriaBuilder.like(
                criteriaBuilder.lower(
                        join.get("lastName")), "%" + query.toLowerCase() + "%")).thenReturn(mockLastNamePredicate);

        doReturn(mockOrPredicate).when(criteriaBuilder).or(any(), any());

        Predicate actual = underTest.hasAuthorNameLike(query).toPredicate(root, criteriaQuery, criteriaBuilder);

        assertThat(actual).isEqualTo(mockOrPredicate);
    }
}