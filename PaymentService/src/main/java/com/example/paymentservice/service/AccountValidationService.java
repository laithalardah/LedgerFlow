package com.example.paymentservice.service;

import java.math.BigDecimal;

public interface AccountValidationService {

    void validateAccount(Long id);

    void validateBalance(Long id , BigDecimal balance);
}
