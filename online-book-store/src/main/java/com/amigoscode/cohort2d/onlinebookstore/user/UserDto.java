package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.address.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDto (
        Long id,

        @NotNull
        String firstName,

        @NotNull
        String lastName,
        // @Email
        @Email
        String email,
        @NotNull
        String password,
        String phoneNumber,
        @NotNull
        String role,
        @Valid
        List<AddressDto> addresses
) {
}
