package com.amigoscode.cohort2d.onlinebookstore.address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddressJpaServiceTest {

    AddressJpaService underTest;

    @Mock
    AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        underTest = new AddressJpaService(addressRepository);
    }

    @Test
    void shouldCreateAddress() {
        // Given
        Address address = new Address(
            null, "street", null, "city", "zipcode", "country"
        );

        // When
        underTest.saveAddress(address);

        //  Then
        verify(addressRepository).save(address);
    }

    @Test
    void shouldDeleteAssress() {
        //Given
        Long id = 5L;

        //When
        underTest.deleteAddress(id);

        //Then
        verify(addressRepository).deleteById(id);
    }

    @Test
    void shouldCheckExistAddressById() {
        // Given
        var id = 6L;

        // When
        underTest.existAddressById(id);

        // Then
        verify(addressRepository).existsById(id);
    }

    @Test
    void shouldGetAddressById() {
        // Given
        var id = 6L;

        // When
        underTest.getAddressById(id);

        // Then
        verify(addressRepository).findById(id);
    }
}
