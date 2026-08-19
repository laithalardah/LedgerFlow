package com.example.paymentservice.messaging;

import com.example.paymentservice.messaging.command.ProcessTransferCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransferPublisher {

    private final JmsTemplate jmsTemplate;
    @Value("${process-transfer-queue}")
    private String QueueName;
    public TransferPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publish(ProcessTransferCommand processTransferCommand) {


        log.info("Publishing to Queue to Process Transfer With ID :" + processTransferCommand.transferId());

        jmsTemplate.convertAndSend(
                QueueName,
                processTransferCommand
        );
    }
}
