package com.example.transactionhistoryservice.messaging.event;

import java.math.BigDecimal;

public record TransactionCreated(
        Long referenceId,
        String referenceType,
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount
) {
}
