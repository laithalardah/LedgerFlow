package com.example.accountservice.messaging;

import com.example.accountservice.messaging.event.TransferCompleted;
import com.example.accountservice.messaging.event.TransferFailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
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

        log.info("Transfer Completed , Sending Back Confirmation");
        jmsTemplate.convertAndSend(
                transferCompletedQueueName,
                event
        );
    }


    public void failed(TransferFailed event) {


        log.info("Transfer Failed , Sending Back Confirmation");
        jmsTemplate.convertAndSend(
                transferFailedQueueName,
                event
        );
    }
}
