package com.example.transactionhistoryservice.exception;

public class AccountServiceNotAvailable extends RuntimeException {

    public AccountServiceNotAvailable(String message) {
        super(message);
    }
}
