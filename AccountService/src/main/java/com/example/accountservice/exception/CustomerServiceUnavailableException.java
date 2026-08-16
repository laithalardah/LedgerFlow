package com.example.accountservice.exception;

public class CustomerServiceUnavailableException extends RuntimeException{

    public CustomerServiceUnavailableException(String message) {
        super(message);
    }
}
