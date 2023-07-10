package com.amigoscode.cohort2d.onlinebookstore.user;

public record UserDto (
        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        String role
) {
}
