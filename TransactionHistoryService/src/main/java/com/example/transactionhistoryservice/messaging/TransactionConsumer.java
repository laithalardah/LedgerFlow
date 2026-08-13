package com.example.transactionhistoryservice.messaging;

import com.example.transactionhistoryservice.messaging.event.TransactionCreated;
import com.example.transactionhistoryservice.messaging.event.TransactionUpdated;
import com.example.transactionhistoryservice.service.TransactionService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    private final TransactionService transactionService;

    public TransactionConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @JmsListener(destination = "${transaction-created-queue}")
    void consume(TransactionCreated transactionCreated) {
        transactionService.createTransaction(transactionCreated);
    }

    @JmsListener(destination = "${transaction-updated-queue}")
    void consume(TransactionUpdated transactionUpdated) {
        transactionService.updateTransaction(transactionUpdated);
    }

}
