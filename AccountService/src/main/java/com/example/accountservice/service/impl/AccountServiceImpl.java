package com.example.accountservice.service.impl;

import com.example.accountservice.entity.AccountEntity;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.InsufficientBalanceException;
import com.example.accountservice.exception.InvalidAmountArgumentException;
import com.example.accountservice.mapper.AccountMapper;
import com.example.accountservice.mapper.CurrencyMapper;
import com.example.accountservice.Utils.CustomerClient;
import com.example.accountservice.model.AccountCreationModel;
import com.example.accountservice.model.AccountModel;
import com.example.accountservice.model.UserModel;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;


@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerClient customerClient;


    AccountServiceImpl(AccountRepository accountRepository , AccountMapper accountMapper ,
                       CustomerClient customerClient){
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.customerClient = customerClient;
    }


    //mapped the currency string to a currency object.
    @Override
    @Transactional
    public AccountModel createAccount(AccountCreationModel accountCreationModel) {

        UserModel userModel = customerClient.getUserInfo(accountCreationModel.userId());

        String currencySymbol = accountCreationModel.currencySymbol();

        Currency currency = CurrencyMapper.currencySymbolMapping(currencySymbol);

        AccountEntity newAccount = accountMapper.toAccountEntity(accountCreationModel);

        newAccount.setUserId(userModel.id());
        newAccount.setCurrency(currency);

        accountRepository.save(newAccount);

        return accountMapper.toAccountModel(newAccount);
    }


    @Override
    public BigDecimal getAccountBalance(Long accountNumber) {

        AccountEntity userAccount =  accountRepository.findById(accountNumber).
                orElseThrow(() -> new AccountNotFoundException("account with number "
                        + accountNumber + " was not found"));

        return userAccount.getBalance();
    }

    @Override
    @Transactional
    public AccountModel Deposit(Long accountNumber , Map<String, BigDecimal> request) {
        AccountEntity account = accountRepository.findById(accountNumber).
                orElseThrow(() -> new AccountNotFoundException("account with number "
                        + accountNumber + " was not found"));

        BigDecimal amount = request.get("amount");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountArgumentException("cannot deposit an amount with value less than or equal to zero");
        }

        BigDecimal newBalance = account.getBalance().add(amount);

        account.setBalance(newBalance);

        accountRepository.save(account);

        return accountMapper.toAccountModel(account);
    }

    @Override
    @Transactional
    public AccountModel WithDraw(Long accountNumber , Map<String, BigDecimal> request) {

        AccountEntity account = accountRepository.findById(accountNumber).
                orElseThrow(() -> new AccountNotFoundException("account with number "
                        + accountNumber + " was not found"));

        BigDecimal amount = request.get("amount");
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountArgumentException("cannot withdraw an amount with value less than or equal to zero");
        }

        BigDecimal accountBalance = account.getBalance();

        if(amount.compareTo(accountBalance) > 0) {
            throw new InsufficientBalanceException("Insufficient balance in your account");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);

        account.setBalance(newBalance);

        accountRepository.save(account);

        return accountMapper.toAccountModel(account);
    }

    @Override
    public UserModel getAccountUserInfo(Long accountNumber) {
        AccountEntity account = accountRepository.findById(accountNumber).
                orElseThrow(() -> new AccountNotFoundException("account with number "
                        + accountNumber + " was not found"));

        return customerClient.getUserInfo(account.getUserId());
    }

    @Override
    public List<AccountModel> getUserAccounts(Long userId) {
        List<AccountEntity> userAccounts = accountRepository.findAllByUserId(userId);

        return userAccounts.stream().
                map(accountMapper::toAccountModel)
                .toList();
    }


}
