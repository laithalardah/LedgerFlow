package com.example.transactionhistoryservice.service.impl;

import com.example.transactionhistoryservice.client.AccountClient;
import com.example.transactionhistoryservice.entity.TransactionEntity;
import com.example.transactionhistoryservice.enums.ReferenceType;
import com.example.transactionhistoryservice.exception.TransactionNotFoundException;
import com.example.transactionhistoryservice.mapper.ReferenceTypeMapper;
import com.example.transactionhistoryservice.mapper.TransactionMapper;
import com.example.transactionhistoryservice.messaging.event.TransactionCreated;
import com.example.transactionhistoryservice.messaging.event.TransactionUpdated;
import com.example.transactionhistoryservice.model.AccountModel;
import com.example.transactionhistoryservice.model.TransactionModel;
import com.example.transactionhistoryservice.repository.TransactionRepository;
import com.example.transactionhistoryservice.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final TransactionMapper transactionMapper;
    private final ReferenceTypeMapper referenceTypeMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountClient accountClient,
                                  TransactionMapper transactionMapper, ReferenceTypeMapper referenceTypeMapper) {
        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
        this.transactionMapper = transactionMapper;
        this.referenceTypeMapper = referenceTypeMapper;
    }


    @Override
    @Transactional
    public void createTransaction(TransactionCreated transactionCreated){
        TransactionEntity transactionEntity =
                transactionMapper.toTransactionEntity(transactionCreated);

        if(transactionRepository.findByReferenceIdAndReferenceType(
                transactionEntity.getReferenceId() ,
                transactionEntity.getReferenceType())
                .isPresent()) return;

        log.info("Transaction Created");
        transactionEntity.setReferenceType(referenceTypeMapper.toReferenceType((transactionCreated).referenceType()));

        transactionRepository.save(transactionEntity);
    }

    @Override
    @Transactional
    public void updateTransaction(TransactionUpdated transactionUpdated) {

        ReferenceType referenceType = referenceTypeMapper.toReferenceType(
                transactionUpdated.referenceType()
        );

        log.info("Fetching Transaction...");
        TransactionEntity transactionEntity = transactionRepository.findByReferenceIdAndReferenceType(
                transactionUpdated.referenceId(),
                referenceType
        ).orElseThrow(()->
                new TransactionNotFoundException("Transaction Not Inserted Yet"));


        transactionEntity.setStatus(transactionUpdated.status());

        log.info("Transaction Updated!");
        transactionRepository.save(transactionEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionModel> getUserTransactionHistory(Long userId , Pageable pageable) {

        log.info("Fetching User Account Numbers...");
        List<AccountModel> userAccountModels = accountClient.getUserAccounts(userId);

        List<Long> userAccountNumbers = (userAccountModels != null ? userAccountModels :Collections.<AccountModel>emptyList())
                .stream()
                .map(AccountModel::accountNumber)
                .toList();

        if(userAccountNumbers.isEmpty()) return Page.empty(pageable);

        log.info("Returning User Transactions...");
        return transactionRepository.findByDebtorAccountNumberInOrCreditorAccountNumberIn(userAccountNumbers,
                        userAccountNumbers , pageable).map(transactionMapper::toTransactionModel);
    }
}
