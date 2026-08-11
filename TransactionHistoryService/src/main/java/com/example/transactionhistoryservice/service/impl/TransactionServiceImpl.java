package com.example.transactionhistoryservice.service.impl;

import com.example.transactionhistoryservice.client.AccountClient;
import com.example.transactionhistoryservice.entity.TransactionEntity;
import com.example.transactionhistoryservice.mapper.TransactionMapper;
import com.example.transactionhistoryservice.model.AccountModel;
import com.example.transactionhistoryservice.model.TransactionCreationModel;
import com.example.transactionhistoryservice.model.TransactionModel;
import com.example.transactionhistoryservice.repository.TransactionRepository;
import com.example.transactionhistoryservice.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountClient accountClient,
                                  TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
        this.transactionMapper = transactionMapper;
    }


    @Override
    public void CreateTransaction(TransactionCreationModel transactionCreationModel) {

    }

    @Override
    public void UpdateTransaction(TransactionCreationModel transactionCreationModel) {

    }

    @Override
    public List<TransactionModel> getUserTransactionHistory(Long userId) {

        log.info("Getting User Account Numbers");
        List<Long> userAccountNumbers =  accountClient.getUserAccounts(userId).stream().
                map(AccountModel::accountNumber).toList();

        log.info("Returning User Transactions");
        return transactionRepository.findByDebtorAccountNumberIn(userAccountNumbers).stream().
                map(transactionMapper::toTransactionModel).toList();
    }
}
