package com.amigoscode.cohort2d.onlinebookstore.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserJpaServiceTest {

    private UserJpaService underTest;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        underTest = new UserJpaService(userRepository);
    }

    @Test
    void getUsers() {
        // When
            underTest.getUsers();

        //  Then
        verify(userRepository).findAll();
    }

    @Test
    void getUserById() {
        // Given
        Long id = 5L;

        // When
        underTest.getUserById(id);

        //  Then
        verify(userRepository).findById(id);
    }

    @Test
    void createUser() {
        // Given
        User user = new User(
                null, "John", "Doe", "ali@gmail.com", "password", "", ""
                , Collections.emptyList());

        // When
        underTest.saveUser(user);

        //  Then
        verify(userRepository).save(user);
    }

    @Test
    void existUserByEmail() {
        // Given
        String email = "foo@gmail.com";

        // When
        underTest.existUserByEmail(email);

        // Then
        verify(userRepository).existsUserByEmail(email);
    }

}