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
        underTest.createAddress(address);

        //  Then
        verify(addressRepository).save(address);
    }
}
