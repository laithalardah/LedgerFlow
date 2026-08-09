package com.example.paymentservice.exception;

public class AccountServiceNotAvailableException extends RuntimeException{

    public AccountServiceNotAvailableException(String message) {
        super(message);
    }
}
