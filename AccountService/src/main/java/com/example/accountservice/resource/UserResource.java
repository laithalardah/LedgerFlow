package com.example.accountservice.resource;

public record UserResource(
        Long id,
        String userName,
        String firstName,
        String lastName,
        String email
) {
}
