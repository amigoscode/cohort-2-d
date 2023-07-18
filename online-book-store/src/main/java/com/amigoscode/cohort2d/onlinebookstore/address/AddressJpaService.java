package com.amigoscode.cohort2d.onlinebookstore.address;

import org.springframework.stereotype.Repository;

@Repository
public class AddressJpaService implements AddressDao {

    private final AddressRepository addressRepository;

    public AddressJpaService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }
}
