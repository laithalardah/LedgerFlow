package com.example.transactionhistoryservice.service;

import com.example.transactionhistoryservice.messaging.event.TransactionCreated;
import com.example.transactionhistoryservice.messaging.event.TransactionUpdated;
import com.example.transactionhistoryservice.model.TransactionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    void createTransaction(TransactionCreated transactionCreated);

    void updateTransaction(TransactionUpdated transactionUpdated);

    Page<TransactionModel> getUserTransactionHistory(Long userId , Pageable pageable);
}
