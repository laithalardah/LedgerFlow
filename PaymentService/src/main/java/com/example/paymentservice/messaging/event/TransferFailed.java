package com.example.paymentservice.messaging.event;


import java.io.Serializable;

public record TransferFailed(
        Long transferId,
        String reason
) implements Serializable {
}