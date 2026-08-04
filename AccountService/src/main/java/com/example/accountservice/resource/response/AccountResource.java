package com.example.accountservice.resource.response;

import com.example.accountservice.enums.AccountType;

import java.math.BigDecimal;
import java.util.Currency;

public record AccountResource(
        Long accountNumber,
        AccountType accountType,
        Currency currency,
        Long userId,
        BigDecimal balance
) {
}
