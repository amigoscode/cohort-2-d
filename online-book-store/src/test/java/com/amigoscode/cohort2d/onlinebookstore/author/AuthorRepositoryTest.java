package com.amigoscode.cohort2d.onlinebookstore.author;

import com.amigoscode.cohort2d.onlinebookstore.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private AuthorRepository underTest;

    @BeforeEach
    void setUp(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfAuthorExistByFirstNameAndLastName() {
        // Given
        String firstName = "John";
        String lastName = "Doe";

        Author author = new Author(1L, firstName, lastName);
        underTest.save(author);

        // When
        boolean actual = underTest.existsAuthorByFirstNameAndLastName(firstName, lastName);

        // Then
        assertThat(actual).isTrue();
    }

}