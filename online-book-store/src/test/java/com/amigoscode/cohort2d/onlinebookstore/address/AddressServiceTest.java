package com.amigoscode.cohort2d.onlinebookstore.address;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.RequestValidationException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    AddressService underTest;

    @Mock
    AddressDao addressDao;

    @BeforeEach
    void setUp() {
        underTest = new AddressService(addressDao);
    }

    @Test
    void shouldCreateAddress() {
        //Given
        var addressRequest = new AddressDto(
                null,
                "1 main street",
                "",
                "Houston",
                "74225",
                "USA"
        );

        //When
        underTest.saveAddress(addressRequest);

        //Then
        ArgumentCaptor<Address> addressArgumentCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressDao).saveAddress(addressArgumentCaptor.capture());
        Address capturedAddress = addressArgumentCaptor.getValue();

        assertThat(capturedAddress.getId()).isNull();
        assertEqual(addressRequest, capturedAddress);
    }

    @Test
    void shoutGetAddressById() {
        Long id = 5L;
        Address address = new Address(id, "1 main", "", "Houston", "14652", "USA");

        when(addressDao.getAddressById(id)).thenReturn(Optional.of(address));

        AddressDto expected = AddressDtoMapper.INSTANCE.modelToDto(address);

        // When
        AddressDto actual = underTest.getAddressById(id);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowWhenGetAddressByIdReturnsEmptyOptional() {
        Long id = 5L;

        when(addressDao.getAddressById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getAddressById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("address with id [%s] not found".formatted(id));
    }

    @Test
    void shouldDeleteAddressById() {
        // Given
        var id = 5L;
        when(addressDao.existAddressById(id)).thenReturn(true);

        //When
        underTest.deleteAddress(id);

        // Then
        verify(addressDao).existAddressById(id);
        verify(addressDao).deleteAddress(id);
    }

    @Test
    void shouldThrowWhenDeleteAddressByIdNotExists() {
        // Given
        var id = 5L;
        when(addressDao.existAddressById(id)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> underTest.deleteAddress(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("address with id [%s] not found".formatted(id));
    }

    @Test
    void shouldUpdateAddress() {
        // Given
        var id = 5L;
        Address address = new Address(
                id,
                "12 main street",
                "",
                "Houston",
                "74225",
                "USA");

        when(addressDao.getAddressById(id)).thenReturn(Optional.of(address));

        var addressRequest = new AddressDto(
                null,
                "1 main street",
                "",
                "Houston",
                "74225",
                "USA"
        );

        // When
        underTest.updateAddress(id, addressRequest);

        // Then
        verify(addressDao).getAddressById(id);

        ArgumentCaptor<Address> addressArgumentCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressDao).saveAddress(addressArgumentCaptor.capture());
        Address capturedAddress = addressArgumentCaptor.getValue();

        assertThat(capturedAddress.getId()).isEqualTo(id);
        assertEqual(addressRequest, capturedAddress);
    }

    @Test
    void shouldThrowWhenUpdateAddressNotExist() {
        // Given
        var id = 5L;
        when(addressDao.getAddressById(id)).thenReturn(Optional.empty());

        var addressRequest = new AddressDto(
                null,
                "1 main street",
                "",
                "Houston",
                "74225",
                "USA"
        );

        // Then
        assertThatThrownBy(() -> underTest.updateAddress(id, addressRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("address with id [%s] not found".formatted(id));
    }

    @Test
    void shouldThrowWhenUpdateAddressNotDataChanges() {
        // Given
        var id = 5L;
        Address address = new Address(
                id,
                "12 main street",
                "",
                "Houston",
                "74225",
                "USA");

        when(addressDao.getAddressById(id)).thenReturn(Optional.of(address));

        var addressRequest = new AddressDto(
                null,
                "12 main street",
                "",
                "Houston",
                "74225",
                "USA"
        );

        // Then
        assertThatThrownBy(() -> underTest.updateAddress(id, addressRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");
    }


    private static void assertEqual(AddressDto addressRequest, Address capturedAddress) {
        assertThat(capturedAddress.getStreet()).isNotNull();
        assertThat(capturedAddress.getStreet()).isEqualTo(addressRequest.street());
        assertThat(capturedAddress.getSecondLine()).isEqualTo(addressRequest.secondLine());
        assertThat(capturedAddress.getCity()).isEqualTo(addressRequest.city());
        assertThat(capturedAddress.getZipCode()).isEqualTo(addressRequest.zipCode());
        assertThat(capturedAddress.getCountry()).isEqualTo(addressRequest.country());
    }
}
