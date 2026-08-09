package com.example.paymentservice.messaging;

import com.example.paymentservice.messaging.event.TransferCompleted;
import com.example.paymentservice.messaging.event.TransferFailed;
import com.example.paymentservice.service.TransferEventService;
import org.springframework.jms.annotation.JmsListener;

public class TransferEventConsumer {

    private final TransferEventService transferEventService;

    public TransferEventConsumer(TransferEventService transferEventService) {
        this.transferEventService = transferEventService;
    }

    @JmsListener(destination = "${transfer-completed-queue}")
    public void consume(TransferCompleted transferCompleted) {
        transferEventService.handleTransferCompleted(transferCompleted);
    }

    @JmsListener(destination = "${transfer-failed-queue}")
    public void consume(TransferFailed transferFailed){
        transferEventService.handleTransferFailed(transferFailed);
    }
}

