package com.amigoscode.cohort2d.onlinebookstore.address;

import java.util.Optional;

public interface AddressDao {
    Address saveAddress(Address address);

    void  deleteAddress(Long id);

    boolean existAddressById(long id);

    Optional<Address> getAddressById(Long addressId);

    Address getAddressReferenceById(Long addressId);
}
