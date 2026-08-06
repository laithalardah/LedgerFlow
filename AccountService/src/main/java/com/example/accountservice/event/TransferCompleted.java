package com.example.accountservice.event;

import java.math.BigDecimal;

public record TransferCompleted(
        Long transferId,
        BigDecimal amount
){
}
