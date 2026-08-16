package com.example.paymentservice.model;

import com.example.paymentservice.enums.Status;

import java.math.BigDecimal;

public record TransferModel(
        Long id,
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount,
        Status status
) {
}
