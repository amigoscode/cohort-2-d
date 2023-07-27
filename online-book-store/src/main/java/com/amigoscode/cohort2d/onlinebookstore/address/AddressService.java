package com.amigoscode.cohort2d.onlinebookstore.address;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.RequestValidationException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressDao addressDao;

    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public AddressDto saveAddress(AddressDto addressRequest) {
        var createdAddress = addressDao.saveAddress(AddressDtoMapper.INSTANCE.dtoToModel(addressRequest));
        return AddressDtoMapper.INSTANCE.modelToDto(createdAddress);
    }

    public AddressDto getAddressById(Long id) {
        return AddressDtoMapper.INSTANCE.modelToDto(getAddress(id));
    }

    public void deleteAddress(Long addressId) {
        if (!addressDao.existAddressById(addressId)) {
            throw new ResourceNotFoundException("address with id [%s] not found".formatted(addressId));
        }
        addressDao.deleteAddress(addressId);
    }

    public AddressDto updateAddress(Long addressId, AddressDto addressDto) {
        Address address = getAddress(addressId);
        if (
            address.getStreet().equals(addressDto.street()) &&
            address.getSecondLine().equals(addressDto.secondLine()) &&
            address.getCity().equals(addressDto.city()) &&
            address.getZipCode().equals(addressDto.zipCode()) &&
            address.getCountry().equals(addressDto.country())
        ) {
            throw new RequestValidationException("no data changes found");
        }
        address.setStreet(addressDto.street());
        address.setSecondLine(addressDto.secondLine());
        address.setCity(addressDto.city());
        address.setZipCode(addressDto.zipCode());
        address.setCountry(addressDto.country());
        var updatedAddress = addressDao.saveAddress(address);

        return AddressDtoMapper.INSTANCE.modelToDto(updatedAddress);
    }

    private Address getAddress(Long id) {
        return addressDao.getAddressById(id)
                .orElseThrow(() -> new ResourceNotFoundException("address with id [%s] not found".formatted(id)));
    }
}
