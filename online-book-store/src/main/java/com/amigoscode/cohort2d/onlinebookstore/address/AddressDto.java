package com.amigoscode.cohort2d.onlinebookstore.address;

import jakarta.validation.constraints.NotNull;

public record AddressDto(
        Long id,
        @NotNull
        String street,
        String secondLine,
        @NotNull
        String city,
        @NotNull
        String zipCode,
        @NotNull
        String country
) {}
