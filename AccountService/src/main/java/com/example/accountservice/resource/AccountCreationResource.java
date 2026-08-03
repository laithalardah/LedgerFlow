package com.example.accountservice.resource;

import com.example.accountservice.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountCreationResource(
        @NotNull
        @NotBlank
        Long userId,
        @NotBlank
        @NotNull
        AccountType accountType,
        @NotBlank
        @NotNull
        String currencySymbol
) {
}
