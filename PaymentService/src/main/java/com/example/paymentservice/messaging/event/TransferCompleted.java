package com.example.paymentservice.messaging.event;

import java.io.Serializable;
import java.math.BigDecimal;

public record TransferCompleted(
        Long transferId,
        BigDecimal amount
) implements Serializable {
}
