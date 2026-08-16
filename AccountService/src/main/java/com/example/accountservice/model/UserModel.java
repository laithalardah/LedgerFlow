package com.example.accountservice.model;

public record UserModel(
        Long id,
        String userName,
        String firstName,
        String lastName,
        String email
) {
}
