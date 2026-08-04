package com.example.accountservice.resource;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AmountRequest(
        @NotNull(message = "amount cant be null")
        @DecimalMin(value = "0.0" , inclusive = false , message = "amount cant be negative")
        BigDecimal amount
) {
}
