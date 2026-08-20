package com.example.paymentservice.service.impl;

import com.example.paymentservice.entity.TransferEntity;
import com.example.paymentservice.enums.Status;
import com.example.paymentservice.exception.TransferNotFoundException;
import com.example.paymentservice.messaging.event.TransferCompleted;
import com.example.paymentservice.messaging.event.TransferFailed;
import com.example.paymentservice.repository.TransferRepository;
import com.example.paymentservice.service.TransactionEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferEventServiceImplTest {
    @Mock private TransferRepository transferRepository;
    @Mock private TransactionEventService transactionEventService;
    @InjectMocks private TransferEventServiceImpl service;
    @Test void marksTransferCompleteAndPublishesUpdate() {
        TransferEntity transfer = transfer(1L);
        when(transferRepository.findById(1L)).thenReturn(Optional.of(transfer));
        service.handleTransferCompleted(new TransferCompleted(1L, BigDecimal.TEN));
        assertThat(transfer.getStatus()).isEqualTo(Status.COMPLETE);
        verify(transferRepository).save(transfer);
        verify(transactionEventService).handleTransactionUpdated(argThat(event -> event.status() == Status.COMPLETE));
    }
    @Test void marksTransferFailedAndThrowsForUnknownTransfer() {
        TransferEntity transfer = transfer(1L);
        when(transferRepository.findById(1L)).thenReturn(Optional.of(transfer));
        service.handleTransferFailed(new TransferFailed(1L, "declined"));
        assertThat(transfer.getStatus()).isEqualTo(Status.FAILED);
        when(transferRepository.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.handleTransferCompleted(new TransferCompleted(2L, BigDecimal.ONE)))
                .isInstanceOf(TransferNotFoundException.class);
    }
    private TransferEntity transfer(Long id) { TransferEntity transfer = new TransferEntity(); transfer.setId(id); return transfer; }
}
