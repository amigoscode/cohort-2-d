package com.amigoscode.cohort2d.onlinebookstore.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository underTest;


    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfCategoryExistsByName() {
        // Given
        String name = "Ulysses";
        Category category = new Category(1L, name, "A modernist novel by Irish writer James Joyce.");
        underTest.save(category);

        // When
        boolean actual = underTest.existsCategoryByName(name);

        // Then
        assertThat(actual).isTrue();
    }
}