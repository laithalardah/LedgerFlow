package com.example.customerservice.model;

public record UserModel(

        Long id,
        String userName,
        String firstName,
        String lastName,
        String email,
        Integer age
) {
}
