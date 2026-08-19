package com.example.paymentservice.exception;

public class CurrencyMismatchException extends RuntimeException{
    public CurrencyMismatchException(String message) {
        super(message);
    }
}
