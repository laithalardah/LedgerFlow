package com.example.accountservice.resource;

import com.example.accountservice.enums.AccountType;

import java.util.Currency;

public record AccountResource(
        Long accountNumber,
        AccountType accountType,
        Currency currency,
        Long userId
) {
}
