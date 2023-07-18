package com.amigoscode.cohort2d.onlinebookstore.address;

import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressDao addressDao;

    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public AddressDto createAddress(AddressDto addressRequest) {
        var createdAddress = addressDao.createAddress(AddressDtoMapper.INSTANCE.dtoToModel(addressRequest));
        return AddressDtoMapper.INSTANCE.modelToDto(createdAddress);
    }
}
