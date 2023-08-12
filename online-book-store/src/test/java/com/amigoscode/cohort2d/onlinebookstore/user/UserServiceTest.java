package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.address.Address;
import com.amigoscode.cohort2d.onlinebookstore.address.AddressDto;
import com.amigoscode.cohort2d.onlinebookstore.address.AddressDtoMapper;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                id, "John", "Doe", "jd@gmail.com", "password", "", "", Collections.emptyList()
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
        UserDto request = new UserDto(
                null,
                "John",
                "Doe",
                email,
                "password",
                "",
                "role_user",
                Collections.emptyList());
        underTest.createUser(request);

        //  Then
        verify(userDao).existUserByEmail(email);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).saveUser(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getId()).isNull();
        assertThat(capturedUser.getFirstName()).isEqualTo(request.firstName());
        assertThat(capturedUser.getEmail()).isEqualTo(request.email());

    }

    @Test
    void shouldThrowWhenCreateUserWithExistingEmail() {
        // Given
        var email = "anne@mail.com";
        when(userDao.existUserByEmail(email)).thenReturn(true);

        // When
        UserDto request = new UserDto(1L,
                "John",
                "Doe",
                email,
                "password",
                "",
                "role_user",
                Collections.emptyList());

        //  Then
        assertThatThrownBy(() -> underTest.createUser(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        verify(userDao, never()).saveUser(any());
    }

    @Test
    void shouldAddUserAddress() {
        //Given
        Long id = 10L;
        User user = new User(
                id, "John", "Doe", "jd@gmail.com", "password", "", "", new ArrayList<>()
        );
        when(userDao.getUserById(id)).thenReturn(Optional.of(user));

        var addressRequestDto = new AddressDto(null, "street1", "", "city", "zipcode", "country");


        // When
        underTest.addAddress(id, addressRequestDto);

        // Then
        verify(userDao).getUserById(id);
        verify(userDao).saveUser(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).saveUser(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getId()).isEqualTo(id);
        assertThat(capturedUser.getAddresses().get(0).getStreet()).isEqualTo(addressRequestDto.street());
        assertThat(capturedUser.getAddresses().get(0).getCity()).isEqualTo(addressRequestDto.city());
        assertThat(capturedUser.getAddresses().get(0).getZipCode()).isEqualTo(addressRequestDto.zipCode());
        assertThat(capturedUser.getAddresses().get(0).getCountry()).isEqualTo(addressRequestDto.country());
    }

    @Test
    void shouldGetUserAddresses() {
        // Given
        Long id = 10L;
        Address address1 = new Address(1L, "street1", "line2", "city1", "zipcode1", "country1");
        Address address2 = new Address(2L, "street2", "line2", "city2", "zipcode2", "country2");
        User user = new User(
                id, "John", "Doe", "jd@gmail.com", "password", "", "", List.of(address1, address2)
        );

        when(userDao.getUserById(id)).thenReturn(Optional.of(user));

        List<AddressDto> expected = AddressDtoMapper.INSTANCE.modelToDto(user.getAddresses());

        // When
        List<AddressDto> actual = underTest.getUserAddresses(id);

        //  Then
        verify(userDao).getUserById(id);
        assertThat(actual).isEqualTo(expected);

    }

}