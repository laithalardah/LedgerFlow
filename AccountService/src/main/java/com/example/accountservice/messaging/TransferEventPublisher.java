package com.example.accountservice.messaging;

import com.example.accountservice.messaging.event.TransferCompleted;
import com.example.accountservice.messaging.event.TransferFailed;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransferEventPublisher {

    private final JmsTemplate jmsTemplate;


    public TransferEventPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public void completed(TransferCompleted event) {

        jmsTemplate.convertAndSend(
                "${transfer-completed-queue}",
                event
        );

    }


    public void failed(TransferFailed event) {

        jmsTemplate.convertAndSend(
                "${transfer-failed-queue}",
                event
        );

    }
}
