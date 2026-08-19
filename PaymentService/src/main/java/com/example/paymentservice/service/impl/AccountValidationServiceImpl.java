package com.example.paymentservice.service.impl;

import com.example.paymentservice.client.AccountClient;
import com.example.paymentservice.exception.BalanceNotFoundException;
import com.example.paymentservice.exception.InsufficientBalanceException;
import com.example.paymentservice.model.AccountModel;
import com.example.paymentservice.service.AccountValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountValidationServiceImpl implements AccountValidationService {

    public final AccountClient accountClient;

    public AccountValidationServiceImpl(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @Override
    public AccountModel validateAccount(Long id) {

       return accountClient.validateAccount(id);
    }

    @Override
    public void validateBalance(Long id , BigDecimal balance) {

        BigDecimal accountBalance = accountClient.getAccountBalance(id);

        if(accountBalance == null) throw new BalanceNotFoundException("Account Number " +id +" Returned Null");

        if(accountBalance.compareTo(balance) < 0)
            throw new InsufficientBalanceException("Account does not have Sufficient Balance to complete Transfer");
    }

}
