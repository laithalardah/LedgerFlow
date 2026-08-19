package com.example.paymentservice.service;

import com.example.paymentservice.model.AccountModel;

import java.math.BigDecimal;

public interface AccountValidationService {

    AccountModel validateAccount(Long id);

    void validateBalance(Long id , BigDecimal balance);
}
