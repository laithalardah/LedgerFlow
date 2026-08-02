package com.example.customerservice.resource.response;

import jakarta.validation.constraints.NotBlank;

public record UserResource(
        @NotBlank(message = "enter id")
        Long id,
        String userName,
        String firstName,
        String lastName,
        String email
) {
}
