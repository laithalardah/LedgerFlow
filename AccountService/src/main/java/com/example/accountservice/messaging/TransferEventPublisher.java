package com.example.accountservice.messaging;

import com.example.accountservice.messaging.event.TransferCompleted;
import com.example.accountservice.messaging.event.TransferFailed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransferEventPublisher {

    private final JmsTemplate jmsTemplate;
    @Value("${transfer-completed-queue}")
    String transferCompletedQueueName;

    @Value("${transfer-failed-queue}")
    String transferFailedQueueName;

    public TransferEventPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public void completed(TransferCompleted event) {

        jmsTemplate.convertAndSend(
                transferCompletedQueueName,
                event
        );
    }


    public void failed(TransferFailed event) {

        jmsTemplate.convertAndSend(
                transferFailedQueueName,
                event
        );
    }
}
