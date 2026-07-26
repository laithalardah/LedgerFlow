package com.example.customerservice.Model.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreationDto(

        @NotBlank(message = "enter your first name please")
        String firstName,
        @NotBlank(message = "enter your last name please")
        String lastName,

        @NotBlank(message = "enter a user name")
        String userName,

        @NotBlank(message = "enter an email")
        @Email(message = "enter a valid email please")
        String email,

        byte age
) {
}
