package com.example.paymentservice.exception;

import com.example.paymentservice.service.TransactionEventService;

public class TransferNotFoundException extends RuntimeException{
    public TransferNotFoundException(String message) {
        super(message);
    }
}
