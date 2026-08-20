package com.example.paymentservice.service.impl;

import com.example.paymentservice.messaging.TransactionPublisher;
import com.example.paymentservice.messaging.event.TransactionCreated;
import com.example.paymentservice.messaging.event.TransactionUpdated;
import com.example.paymentservice.service.TransactionEventService;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventServiceImpl implements TransactionEventService {

    private final TransactionPublisher transactionPublisher;

    public TransactionEventServiceImpl(TransactionPublisher transactionPublisher) {
        this.transactionPublisher = transactionPublisher;
    }

    @Override
    public void handleTransactionCreated(TransactionCreated transactionCreated) {
        transactionPublisher.publish(transactionCreated);
    }

    @Override
    public void handleTransactionUpdated(TransactionUpdated transactionUpdated) {
        transactionPublisher.publish(transactionUpdated);

    }
}
