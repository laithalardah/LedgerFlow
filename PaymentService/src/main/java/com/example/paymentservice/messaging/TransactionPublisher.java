package com.example.paymentservice.messaging;

import com.example.paymentservice.messaging.event.TransactionCreated;
import com.example.paymentservice.messaging.event.TransactionUpdated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TransactionPublisher {

    private final JmsTemplate jmsTemplate;

    public TransactionPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Value("${transaction-created-queue}")
    private String CreateTransactionQueueName;

    @Value("${transaction-updated-queue}")
    private String UpdateTransactionQueueName;

    public void publish(TransactionCreated transactionCreated) {
        jmsTemplate.convertAndSend(
                CreateTransactionQueueName ,
                transactionCreated
        );
    }

    public void publish(TransactionUpdated transactionUpdated) {
        jmsTemplate.convertAndSend(
                UpdateTransactionQueueName,
                transactionUpdated
        );
    }
}
