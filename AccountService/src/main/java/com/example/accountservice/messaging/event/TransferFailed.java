package com.example.accountservice.messaging.event;

public record TransferFailed(
        Long transferId,
        String reason
) {
}
