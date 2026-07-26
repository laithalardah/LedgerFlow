package com.example.customerservice.Model.Dto;

import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank(message = "enter id")
        Long id,
        String userName,
        String firstName,
        String lastName,
        String email
) {
}
