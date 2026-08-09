package com.example.paymentservice.model;

import java.math.BigDecimal;

public record TransferCreationModel(
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount
) {
}
