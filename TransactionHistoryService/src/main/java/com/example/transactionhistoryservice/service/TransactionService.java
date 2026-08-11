package com.example.transactionhistoryservice.service;

import com.example.transactionhistoryservice.model.TransactionCreationModel;
import com.example.transactionhistoryservice.model.TransactionModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    void CreateTransaction(TransactionCreationModel transactionCreationModel);

    void UpdateTransaction(TransactionCreationModel transactionCreationModel);

    List<TransactionModel> getUserTransactionHistory(Long userId);
}
