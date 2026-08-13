package com.example.transactionhistoryservice.resource;

import com.example.transactionhistoryservice.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResource(
        Long id,
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount,
        LocalDateTime localDateTime,
        Status status
) {
}
