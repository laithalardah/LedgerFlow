package com.example.transactionhistoryservice.model;

import com.example.transactionhistoryservice.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionModel(
        Long id,
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount,
        LocalDateTime localDateTime,
        Status status
) {
}
