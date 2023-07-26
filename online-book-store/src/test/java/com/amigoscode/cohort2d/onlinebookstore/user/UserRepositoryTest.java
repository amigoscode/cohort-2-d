package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private UserRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void existUserByEmail() {
        // Given
        String email = "test@test.com-" + UUID.randomUUID();
        User user = new User(
                "John",
                "Doe",
                email,
                "password",
                "",
                "customer",
                Collections.emptyList(),
                Collections.emptySet()
        );

        underTest.save(user);

        // When
        var actual = underTest.existsUserByEmail(email);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existUserByEmailFailsWhenEmailNotPresent() {
        // Given
        String email = "jd@gmail.com-" + UUID.randomUUID();

        // When
        var actual = underTest.existsUserByEmail(email);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void existUserById() {
        // Given
        String email = "test@test.com-" + UUID.randomUUID();
        User user = new User(
                "John",
                "Doe",
                email,
                "password",
                "customer",
                "customer",
                Collections.emptyList(),
                Collections.emptySet()
        );

        underTest.save(user);

        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(User::getId)
                .findFirst()
                .orElseThrow();

        // When
        var actual = underTest.existsUserById(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existUserByIdFailsWhenEmailNotPresent() {
        // Given
        Long id = 4L;

        // When
        var actual = underTest.existsUserById(id);

        // Then
        assertThat(actual).isFalse();
    }
}