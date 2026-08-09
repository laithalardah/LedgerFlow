package com.example.accountservice.service;

import com.example.accountservice.messaging.command.ProcessTransferCommand;
import com.example.accountservice.model.AccountCreationModel;
import com.example.accountservice.model.AccountModel;
import com.example.accountservice.model.UserModel;
import com.example.accountservice.resource.request.AmountRequest;
import com.example.accountservice.resource.response.AccountValidationResponse;

import java.math.BigDecimal;
import java.util.List;


public interface AccountService {

    AccountModel createAccount(AccountCreationModel accountCreationModel);

    List<AccountModel> getUserAccounts(Long userId);

    BigDecimal getAccountBalance(Long accountNumber);

    AccountModel deposit(Long accountNumber , AmountRequest request);

    AccountModel withDraw(Long accountNumber , AmountRequest request);

    UserModel getAccountUserInfo(Long accountNumber);

    AccountValidationResponse validateAccount(Long accountNumber);

    void ProcessTransfer(ProcessTransferCommand processTransferCommand);
}
