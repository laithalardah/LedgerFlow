package com.example.accountservice.resource.response;

import java.util.Currency;

public record AccountValidationResponse(
        Long accountNumber,
        Currency currency
) {
}
