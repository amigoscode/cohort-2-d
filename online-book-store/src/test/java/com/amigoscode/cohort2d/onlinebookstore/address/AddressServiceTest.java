package com.amigoscode.cohort2d.onlinebookstore.address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

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
                null,
                "Houston",
                "74225",
                "USA"
        );

        //When
        underTest.createAddress(addressRequest);

        //Then
        ArgumentCaptor<Address> addressArgumentCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressDao).createAddress(addressArgumentCaptor.capture());
        Address capturedAddress = addressArgumentCaptor.getValue();

        assertThat(capturedAddress.getId()).isNull();
        assertThat(capturedAddress.getStreet()).isNotNull();
        assertThat(capturedAddress.getStreet()).isEqualTo(addressRequest.street());
        assertThat(capturedAddress.getCity()).isEqualTo(addressRequest.city());
        assertThat(capturedAddress.getZipCode()).isEqualTo(addressRequest.zipCode());
        assertThat(capturedAddress.getCountry()).isEqualTo(addressRequest.country());
    }
}
