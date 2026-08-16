package com.example.paymentservice.messaging;

import com.example.paymentservice.messaging.event.TransferCompleted;
import com.example.paymentservice.messaging.event.TransferFailed;
import com.example.paymentservice.service.TransferEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransferConsumer {

    private final TransferEventService transferEventService;

    public TransferConsumer(TransferEventService transferEventService) {
        this.transferEventService = transferEventService;
    }

    @JmsListener(destination = "${transfer-completed-queue}")
    public void consume(TransferCompleted transferCompleted) {
        log.info("Consumed Transfer Completion Confirmation");

        transferEventService.handleTransferCompleted(transferCompleted);
    }

    @JmsListener(destination = "${transfer-failed-queue}")
    public void consume(TransferFailed transferFailed){
        log.info("Consumed Transfer Failing Confirmation");

        transferEventService.handleTransferFailed(transferFailed);
    }
}

