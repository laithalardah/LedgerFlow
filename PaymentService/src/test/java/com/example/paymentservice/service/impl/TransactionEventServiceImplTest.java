package com.example.paymentservice.service.impl;

import com.example.paymentservice.messaging.TransactionPublisher;
import com.example.paymentservice.messaging.event.TransactionCreated;
import com.example.paymentservice.messaging.event.TransactionUpdated;
import com.example.paymentservice.enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionEventServiceImplTest {
    @Mock private TransactionPublisher publisher;
    @InjectMocks private TransactionEventServiceImpl service;
    @Test void publishesCreatedAndUpdatedEvents() {
        TransactionCreated created = new TransactionCreated(1L, "Transfer", 2L, 3L, BigDecimal.ONE, LocalDateTime.now());
        TransactionUpdated updated = new TransactionUpdated("Transfer", 1L, Status.COMPLETE);
        service.handleTransactionCreated(created);
        service.handleTransactionUpdated(updated);
        verify(publisher).publish(created);
        verify(publisher).publish(updated);
    }
}
