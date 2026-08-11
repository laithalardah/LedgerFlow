package com.example.transactionhistoryservice.resource.request;

import jakarta.validation.constraints.NotNull;
import org.aspectj.bridge.IMessage;

import java.math.BigDecimal;

public record TransactionCreationResource(
        @NotNull(message = "Enter debtor Account Number")
        Long debtorAccountNumber,
        @NotNull(message = "Enter Creditor Account Number")
        Long creditorAccountNumber,
        @NotNull(message = "Enter Balance")
        BigDecimal amount
) {
}
