package com.example.transactionhistoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AccountServiceNotAvailable.class)
    public ResponseEntity<ErrorResponse> handleAccountServiceNotAvailable(AccountServiceNotAvailable e) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Account Service Not Available",
                e.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.SERVICE_UNAVAILABLE);
    }


    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFound(TransactionNotFoundException e) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Transaction Not Found",
                e.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUser(InvalidUserException e) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Invalid User",
                e.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }




}
