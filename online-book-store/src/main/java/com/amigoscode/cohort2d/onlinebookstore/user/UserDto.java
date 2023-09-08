package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.address.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDto (
        Long id,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,
        // @Email
        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,
        String phoneNumber,
        @NotBlank
        String role,
        @Valid
        List<AddressDto> addresses
) {
}
