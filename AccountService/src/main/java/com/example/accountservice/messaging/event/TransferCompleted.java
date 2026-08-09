package com.example.accountservice.messaging.event;

import java.math.BigDecimal;

public record TransferCompleted(
        Long transferId,
        BigDecimal amount
){
}
