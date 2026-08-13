package com.example.paymentservice.messaging.event;

import com.example.paymentservice.enums.Status;

public record TransactionUpdated(
        String referenceType,
        Long referenceId,
        Status status
) {
}
