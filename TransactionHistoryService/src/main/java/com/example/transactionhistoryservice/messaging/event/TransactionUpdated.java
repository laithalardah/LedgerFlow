package com.example.transactionhistoryservice.messaging.event;

import com.example.transactionhistoryservice.enums.Status;

public record TransactionUpdated(
        String referenceType,
        Long referenceId,
        Status status
) {
}
