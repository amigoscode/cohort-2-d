package com.amigoscode.cohort2d.onlinebookstore.address;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AddressJpaService implements AddressDao {

    private final AddressRepository addressRepository;

    public AddressJpaService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public boolean existAddressById(long id) {
        return addressRepository.existsById(id);
    }

    @Override
    public Optional<Address> getAddressById(Long addressId) {
        return addressRepository.findById(addressId);
    }
}
