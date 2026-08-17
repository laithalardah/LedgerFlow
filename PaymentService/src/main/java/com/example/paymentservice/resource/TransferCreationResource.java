package com.example.paymentservice.resource;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferCreationResource(

        @NotNull(message = "Enter Source Account Number")
        Long debtorAccountNumber,

        @NotNull(message = "Enter Target Account number")
        Long creditorAccountNumber,

        @NotNull(message = "Enter Amount")
        @DecimalMin(value = "0.0" , inclusive = false , message = "amount cant be negative")
        @Digits(integer = 19, fraction = 4, message = "Amount must not exceed 12 integer digits and 4 decimal places")
        BigDecimal amount
) {
}
