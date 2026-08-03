package com.example.accountservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Insufficient balance",
                ex.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(InvalidAmountArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAmountArgument(InvalidAmountArgumentException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Argument",
                ex.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "account number not found",
                ex.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "user not found",
                ex.getMessage()
        );

        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }





}
