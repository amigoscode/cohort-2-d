package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.address.Address;
import com.amigoscode.cohort2d.onlinebookstore.address.AddressDao;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private AddressDao addressDao;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userDao, addressDao);
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
                id, "John", "Doe", "jd@gmail.com", "password", "", "", null, null
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
                "",
                null,
                null);
        underTest.createUser(request);

        //  Then
        verify(userDao).existUserByEmail(email);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).createUser(userArgumentCaptor.capture());
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
                "",
                null,
                null);

        //  Then
        assertThatThrownBy(() -> underTest.createUser(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        verify(userDao, never()).createUser(any());
    }

    @Test
    void shouldAddUserAddresses() {
        //Given
        Long id = 10L;
        User user = new User(
                id, "John", "Doe", "jd@gmail.com", "password", "", "", null, null
        );

        when(userDao.getUserById(id)).thenReturn(Optional.of(user));

        var addressRequest1 = new AddressDto(null, "street1", "", "city", "zipcode", "country");
        var addressRequest2 = new AddressDto(null, "street2", "", "city", "zipcode", "country");

        // When
        UserDto request = new UserDto(
                null,
                "",
                "",
                "",
                "",
                "",
                "",
                addressRequest1,
                addressRequest2);
        underTest.addAddress(id, request);

        //  Then
        verify(userDao).getUserById(id);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).createUser(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getId()).isEqualTo(id);
        assertThat(capturedUser.getBillingAddress()).isNotNull();
        assertThat(capturedUser.getShippingAddress()).isNotNull();
        assertThat(capturedUser.getBillingAddress().getStreet()).isEqualTo(request.billingAddress().street());
        assertThat(capturedUser.getBillingAddress().getCity()).isEqualTo(request.billingAddress().city());
        assertThat(capturedUser.getShippingAddress().getStreet()).isEqualTo(request.shippingAddress().street());
        assertThat(capturedUser.getShippingAddress().getCity()).isEqualTo(request.shippingAddress().city());
    }

    @Test
    void shouldAddSameAddressIfBillingEqualsShipping() {
        //Given
        Long id = 10L;
        User user = new User(
                id, "John", "Doe", "jd@gmail.com", "password", "", "", null, null
        );

        when(userDao.getUserById(id)).thenReturn(Optional.of(user));

        var addressDtoRequest = new AddressDto(null, "street", "", "city", "zipcode", "country");
        var addressResponse = new Address(11L, "street", "", "city", "zipcode", "country");

        when(
                addressDao.createAddress(AddressDtoMapper.INSTANCE.dtoToModel(addressDtoRequest))
        )
        .thenReturn(addressResponse);

        // When
        UserDto request = new UserDto(
                null,
                "",
                "",
                "",
                "",
                "",
                "",
                addressDtoRequest,
                addressDtoRequest);
        underTest.addAddress(id, request);

        //  Then
        verify(userDao).getUserById(id);
        verify(addressDao).createAddress(AddressDtoMapper.INSTANCE.dtoToModel(addressDtoRequest));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).createUser(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getId()).isEqualTo(id);
        assertThat(capturedUser.getBillingAddress()).isNotNull();
        assertThat(capturedUser.getShippingAddress()).isNotNull();
        assertThat(capturedUser.getBillingAddress())
                .isEqualTo(capturedUser.getShippingAddress())
                .isEqualTo(addressResponse);
    }

}