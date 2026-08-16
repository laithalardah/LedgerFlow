package com.example.paymentservice.model;

import java.util.Currency;

public record AccountModel(
        Long accountNumber,
        Currency currency
) {
}
