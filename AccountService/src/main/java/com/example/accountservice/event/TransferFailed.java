package com.example.accountservice.event;

public record TransferFailed(
        Long transferId,
        String reason
) {
}
