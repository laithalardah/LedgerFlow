package com.example.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountServiceNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleAccountServiceNotAvailable(AccountServiceNotAvailableException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Account Service Not Available",
                ex.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.SERVICE_UNAVAILABLE);
    }


    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRequest(DuplicateRequestException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Request Already Made",
                ex.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.PAYMENT_REQUIRED.value(),
                "Account Does Not Have Sufficient Balance",
                ex.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAccount(InvalidAccountException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Account Not Valid",
                ex.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTransferException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransfer(InvalidTransferException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE.value(),
                "Transfer Not Valid",
                ex.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.NOT_ACCEPTABLE);
    }





}
