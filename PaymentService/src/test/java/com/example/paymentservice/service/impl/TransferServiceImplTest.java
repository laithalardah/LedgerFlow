package com.example.paymentservice.service.impl;

import com.example.paymentservice.entity.TransferEntity;
import com.example.paymentservice.enums.Status;
import com.example.paymentservice.exception.DuplicateRequestException;
import com.example.paymentservice.exception.InvalidTransferException;
import com.example.paymentservice.mapper.TransferMapper;
import com.example.paymentservice.messaging.TransferPublisher;
import com.example.paymentservice.model.TransferCreationModel;
import com.example.paymentservice.model.TransferModel;
import com.example.paymentservice.repository.IdempotentKeyRepository;
import com.example.paymentservice.repository.TransferRepository;
import com.example.paymentservice.service.AccountValidationService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {
    @Mock private TransferRepository transferRepository;
    @Mock private AccountValidationService accountValidationService;
    @Mock private TransferMapper transferMapper;
    @Mock private IdempotentKeyRepository idempotentKeyRepository;
    @Mock private TransferPublisher transferPublisher;
    @Mock private TransactionEventService transactionEventService;
    @InjectMocks private TransferServiceImpl service;
    @Test void rejectsDuplicateAndSameAccountRequests() {
        TransferCreationModel transfer = new TransferCreationModel(1L, 2L, BigDecimal.ONE);
        when(idempotentKeyRepository.existsById(9L)).thenReturn(true);
        assertThatThrownBy(() -> service.createTransfer(transfer, 9L)).isInstanceOf(DuplicateRequestException.class);
        when(idempotentKeyRepository.existsById(10L)).thenReturn(false);
        assertThatThrownBy(() -> service.createTransfer(new TransferCreationModel(1L, 1L, BigDecimal.ONE), 10L))
                .isInstanceOf(InvalidTransferException.class);
    }
    @Test void returnsStatusAndDetailsOrThrowsWhenMissing() {
        TransferEntity entity = new TransferEntity(); entity.setId(1L); entity.setStatus(Status.PENDING);
        TransferModel model = new TransferModel(1L, 2L, 3L, BigDecimal.ONE, Status.PENDING);
        when(transferRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(transferMapper.toTransferModel(entity)).thenReturn(model);
        assertThat(service.getTransferStatus(1L)).isEqualTo(Status.PENDING);
        assertThat(service.getTransferDetails(1L)).isEqualTo(model);
        when(transferRepository.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getTransferStatus(2L)).isInstanceOf(InvalidTransferException.class);
    }
}
