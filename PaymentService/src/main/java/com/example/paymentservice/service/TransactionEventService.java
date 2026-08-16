package com.example.paymentservice.service;


import com.example.paymentservice.messaging.event.TransactionCreated;
import com.example.paymentservice.messaging.event.TransactionUpdated;


public interface TransactionEventService {
    void handleTransactionCreated(TransactionCreated transactionCreated);

    void handleTransactionUpdated(TransactionUpdated transactionUpdated);
}