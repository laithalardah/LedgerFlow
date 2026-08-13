package com.example.transactionhistoryservice.service;

import com.example.transactionhistoryservice.messaging.event.TransactionCreated;
import com.example.transactionhistoryservice.messaging.event.TransactionUpdated;
import com.example.transactionhistoryservice.model.TransactionModel;

import java.util.List;

public interface TransactionService {

    void createTransaction(TransactionCreated transactionCreated);

    void updateTransaction(TransactionUpdated transactionUpdated);

    List<TransactionModel> getUserTransactionHistory(Long userId);
}
