package com.amigoscode.cohort2d.onlinebookstore.address;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @DeleteMapping("{addressId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok().body("deleted");
    }

    @PutMapping("{addressId}")
    public ResponseEntity<AddressDto> updateAddress(
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDto addressDto) {
        var updatedAddress = addressService.updateAddress(addressId, addressDto);
        return ResponseEntity.ok().body(updatedAddress);
    }
}
