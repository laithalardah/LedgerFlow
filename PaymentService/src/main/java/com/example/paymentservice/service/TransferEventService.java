package com.example.paymentservice.service;

import com.example.paymentservice.messaging.event.TransferCompleted;
import com.example.paymentservice.messaging.event.TransferFailed;

public interface TransferEventService {

    void handleTransferFailed(TransferFailed transferFailed);
    void handleTransferCompleted(TransferCompleted transferCompleted);
}
