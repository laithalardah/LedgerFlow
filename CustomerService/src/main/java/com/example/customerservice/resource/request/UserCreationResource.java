package com.example.customerservice.resource.request;

import jakarta.validation.constraints.*;

public record UserCreationResource(

        @NotBlank(message = "enter your first name please")
        String firstName,

        @NotBlank(message = "enter your last name please")
        String lastName,

        @NotBlank(message = "enter a user name")
        String userName,

        @NotBlank(message = "enter an email")
        @Email(message = "enter a valid email please")
        String email,

        @NotNull(message = "enter an email")
        @Min(value = 18)
        @Max(value = 100)
        int age
) {
}
