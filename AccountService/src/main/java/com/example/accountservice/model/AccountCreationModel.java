package com.example.accountservice.model;

import com.example.accountservice.enums.AccountType;

public record AccountCreationModel(
        Long userId,
        AccountType accountType,
        String currencySymbol
) {
}
