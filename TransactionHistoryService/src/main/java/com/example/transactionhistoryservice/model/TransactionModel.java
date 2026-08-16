package com.example.transactionhistoryservice.model;

import com.example.transactionhistoryservice.enums.ReferenceType;
import com.example.transactionhistoryservice.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionModel(
        Long id,
        ReferenceType referenceType,
        Long referenceId,
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount,
        LocalDateTime localDateTime,
        Status status
) {
}
