package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userDao);
    }

    @Test
    void shouldGetAllUsers() {
        // When
        underTest.getUsers();

        //  Then
        verify(userDao).getUsers();
    }

    @Test
    void shouldGetUserById() {
        // Given
        Long id = 10L;
        User user = new User(
                id, "John", "Doe", "jd@gmail.com", "password", "", ""
        );

        when(userDao.getUserById(id)).thenReturn(Optional.of(user));

        UserDto expected = UserDTOMapper.INSTANCE.modelToDTO(user);

        // When
        UserDto actual = underTest.getUserById(id);

        //  Then
        verify(userDao).getUserById(id);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowWhenGetUserByIdReturnsEmptyOptional() {
        // Given
        Long id = 5L;
        when(userDao.getUserById(id)).thenReturn(Optional.empty());

        // When
        //  Then
        assertThatThrownBy(() -> underTest.getUserById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User with id [%s] not found".formatted(id));
    }

    @Test
    void shouldCreateUser() {
        // Given
        var email = "anne@mail.com";
        when(userDao.existUserByEmail(email)).thenReturn(false);

        // When
        UserDto request = new UserDto(null, "John", "Doe", email, "password", "", "");
        underTest.createUser(request);

        //  Then
        verify(userDao).existUserByEmail(email);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).createUser(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getId()).isEqualTo(null);
        assertThat(capturedUser.getFirstName()).isEqualTo(request.firstName());
        assertThat(capturedUser.getEmail()).isEqualTo(request.email());
    }

    @Test
    void shouldThrowWhenCreateUserWithExistingEmail() {
        // Given
        var email = "anne@mail.com";
        when(userDao.existUserByEmail(email)).thenReturn(true);

        // When
        UserDto request = new UserDto(1L, "John", "Doe", email, "password", "", "");

        //  Then
        assertThatThrownBy(() -> underTest.createUser(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        verify(userDao, never()).createUser(any());
    }
}