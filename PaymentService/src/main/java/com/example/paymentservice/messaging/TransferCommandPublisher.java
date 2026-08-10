package com.example.paymentservice.messaging;

import com.example.paymentservice.messaging.command.ProcessTransferCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransferCommandPublisher {

    private final JmsTemplate jmsTemplate;
    @Value("${process-trasnfer-queue}")
    String QueueName;
    public TransferCommandPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void Publish(ProcessTransferCommand processTransferCommand) {
        jmsTemplate.convertAndSend(
                QueueName,
                processTransferCommand
        );
    }
}
