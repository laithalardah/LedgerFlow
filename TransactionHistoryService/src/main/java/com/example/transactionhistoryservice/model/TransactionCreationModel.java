package com.example.transactionhistoryservice.model;

import java.math.BigDecimal;

public record TransactionCreationModel(
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount
) {
}
