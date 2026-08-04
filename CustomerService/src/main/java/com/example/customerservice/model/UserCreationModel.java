package com.example.customerservice.model;

public record UserCreationModel(

        String firstName,
        String lastName,
        String userName,
        String email,
        Integer age
) {
}
