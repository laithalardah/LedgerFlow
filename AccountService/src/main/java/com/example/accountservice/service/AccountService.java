package com.example.accountservice.service;

import com.example.accountservice.model.AccountCreationModel;
import com.example.accountservice.model.AccountModel;
import com.example.accountservice.model.UserModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountService {

    AccountModel createAccount(AccountCreationModel accountCreationModel);

    List<AccountModel> getUserAccounts(Long userId);

    BigDecimal getAccountBalance(Long accountNumber);

    AccountModel Deposit(Long accountNumber , Map<String , BigDecimal> request);

    AccountModel WithDraw(Long accountNumber , Map<String , BigDecimal> request);

    UserModel getAccountUserInfo(Long accountNumber);
}
