package com.example.paymentservice.messaging.command;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProcessTransferCommand(
        Long transferId,
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount
) implements Serializable {
}
