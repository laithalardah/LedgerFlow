package com.example.transactionhistoryservice.messaging.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionCreated(
        Long referenceId,
        String referenceType,
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount,
        LocalDateTime createdAt
) {
}
