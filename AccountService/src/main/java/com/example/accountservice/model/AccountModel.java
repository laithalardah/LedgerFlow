package com.example.accountservice.model;

import com.example.accountservice.enums.AccountType;

import java.util.Currency;

public record AccountModel(
        Long accountNumber,
        AccountType accountType,
        Currency currency,
        Long userId
) {
}
