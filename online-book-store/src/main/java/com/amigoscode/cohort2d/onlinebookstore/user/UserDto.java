package com.amigoscode.cohort2d.onlinebookstore.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserDto (
        Long id,

        @NotNull
        String firstName,

        @NotNull
        String lastName,
        // @Email
        @Email
        String email,
        String password,
        String phoneNumber,
        String role
) {
}
