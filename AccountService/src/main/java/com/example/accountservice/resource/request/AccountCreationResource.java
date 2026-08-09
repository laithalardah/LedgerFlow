package com.example.accountservice.resource.request;

import com.example.accountservice.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountCreationResource(

        @NotNull(message = "userId should not be empty")
        Long userId,
        
        @NotNull(message = "accountType should not be Null")
        AccountType accountType,

        @NotBlank(message = "currencySymbol should not be Blank")
        @NotNull(message = "currencySymbol should not be Null")
        String currencySymbol
) {
}
