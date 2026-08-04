package com.example.customerservice.resource.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResource(
        @NotNull(message = "enter id")
        Long id,
        String userName,
        String firstName,
        String lastName,
        String email,
        Integer age
) {
}
