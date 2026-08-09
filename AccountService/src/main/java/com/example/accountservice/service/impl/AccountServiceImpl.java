package com.example.accountservice.service.impl;

import com.example.accountservice.messaging.command.ProcessTransferCommand;
import com.example.accountservice.entity.AccountEntity;
import com.example.accountservice.messaging.event.TransferCompleted;
import com.example.accountservice.messaging.event.TransferFailed;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.InsufficientBalanceException;
import com.example.accountservice.exception.InvalidAmountArgumentException;
import com.example.accountservice.mapper.AccountMapper;
import com.example.accountservice.mapper.CurrencyMapper;
import com.example.accountservice.client.CustomerClient;
import com.example.accountservice.messaging.TransferEventPublisher;
import com.example.accountservice.model.AccountCreationModel;
import com.example.accountservice.model.AccountModel;
import com.example.accountservice.model.UserModel;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.resource.request.AmountRequest;
import com.example.accountservice.resource.response.AccountValidationResponse;
import com.example.accountservice.service.AccountService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;



@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerClient customerClient;
    private final TransferEventPublisher transferEventPublisher;


    AccountServiceImpl(AccountRepository accountRepository , AccountMapper accountMapper ,
                       CustomerClient customerClient , TransferEventPublisher transferEventPublisher ){
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.customerClient = customerClient;
        this.transferEventPublisher = transferEventPublisher;
    }


    //mapped the currency string to a currency object.
    /* this will cause load on the jdbc connection pool, it is better to call customer client before the transaction.
    Real production software probably uses jwt and validate it in api gateway or something , user id should not be
    sent via JSON or request .*/

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
    @Transactional(readOnly = true)
    public BigDecimal getAccountBalance(Long accountNumber) {

        AccountEntity userAccount =  accountRepository.findById(accountNumber).
                orElseThrow(() -> new AccountNotFoundException("account with number "
                        + accountNumber + " was not found"));

        return userAccount.getBalance();
    }

    @Override
    @Transactional
    public AccountModel deposit(Long accountNumber , AmountRequest request) {
        AccountEntity account = accountRepository.findById(accountNumber).
                orElseThrow(() -> new AccountNotFoundException("account with number "
                        + accountNumber + " was not found"));

        BigDecimal amount = request.amount();

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
    public AccountModel withDraw(Long accountNumber , AmountRequest request) {

        AccountEntity account = accountRepository.findById(accountNumber).
                orElseThrow(() -> new AccountNotFoundException("account with number "
                        + accountNumber + " was not found"));

        BigDecimal amount = request.amount();

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
    @Transactional(readOnly = true)
    public UserModel getAccountUserInfo(Long accountNumber) {
        AccountEntity account = accountRepository.findById(accountNumber).
                orElseThrow(() -> new AccountNotFoundException("account with number "
                        + accountNumber + " was not found"));

        return customerClient.getUserInfo(account.getUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountValidationResponse validateAccount(Long accountNumber) {

        AccountEntity account = accountRepository.findById(accountNumber).
                orElseThrow(() -> new AccountNotFoundException("account with number "
                        + accountNumber + " was not found"));

        return new AccountValidationResponse(account.getAccountNumber() , account.getCurrency());
    }

    @Override
    @Transactional
    public void ProcessTransfer(ProcessTransferCommand processTransferCommand) {
        try {

            // we already made sure that accounts exists

            AmountRequest amount = new AmountRequest(processTransferCommand.amount());

            withDraw(processTransferCommand.creditorAccountNumber() , amount);

            deposit(processTransferCommand.creditorAccountNumber() , amount);

            transferEventPublisher.completed(new TransferCompleted(
                    processTransferCommand.transferId(),
                    processTransferCommand.amount()
            ));


        }
        catch(Exception e) {

            transferEventPublisher.failed(new TransferFailed(
                    processTransferCommand.transferId(),
                    e.getMessage()
            ));

        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountModel> getUserAccounts(Long userId) {
        List<AccountEntity> userAccounts = accountRepository.findAllByUserId(userId);

        return userAccounts.stream().
                map(accountMapper::toAccountModel)
                .toList();
    }


}
