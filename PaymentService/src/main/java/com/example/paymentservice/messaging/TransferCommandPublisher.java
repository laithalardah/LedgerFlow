package com.example.paymentservice.messaging;

import com.example.paymentservice.messaging.command.ProcessTransferCommand;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransferCommandPublisher {

    private final JmsTemplate jmsTemplate;

    public TransferCommandPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void Publish(ProcessTransferCommand processTransferCommand) {
        jmsTemplate.convertAndSend(
                "${process-trasnfer-queue}",
                processTransferCommand
        );
    }
}
