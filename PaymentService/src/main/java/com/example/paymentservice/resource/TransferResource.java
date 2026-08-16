package com.example.paymentservice.resource;

import com.example.paymentservice.enums.Status;

import java.math.BigDecimal;

public record TransferResource(
        Long id,
        Long debtorAccountNumber,
        Long creditorAccountNumber,
        BigDecimal amount,
        Status status
){
}
