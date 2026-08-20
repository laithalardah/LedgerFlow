package com.example.transactionhistoryservice.service.impl;

import com.example.transactionhistoryservice.client.AccountClient;
import com.example.transactionhistoryservice.entity.TransactionEntity;
import com.example.transactionhistoryservice.enums.ReferenceType;
import com.example.transactionhistoryservice.enums.Status;
import com.example.transactionhistoryservice.exception.TransactionNotFoundException;
import com.example.transactionhistoryservice.mapper.ReferenceTypeMapper;
import com.example.transactionhistoryservice.mapper.TransactionMapper;
import com.example.transactionhistoryservice.messaging.event.TransactionCreated;
import com.example.transactionhistoryservice.messaging.event.TransactionUpdated;
import com.example.transactionhistoryservice.model.AccountModel;
import com.example.transactionhistoryservice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock private TransactionRepository repository;
    @Mock private AccountClient accountClient;
    @Mock private TransactionMapper transactionMapper;
    @Mock private ReferenceTypeMapper referenceTypeMapper;
    @InjectMocks private TransactionServiceImpl service;
    @Test void savesNewTransactionButIgnoresDuplicate() {
        TransactionCreated event = new TransactionCreated(1L, "Transfer", 2L, 3L, BigDecimal.ONE, LocalDateTime.now());
        TransactionEntity entity = new TransactionEntity(); entity.setReferenceId(1L); entity.setReferenceType(ReferenceType.TRANSFER);
        when(transactionMapper.toTransactionEntity(event)).thenReturn(entity);
        when(repository.findByReferenceIdAndReferenceType(1L, ReferenceType.TRANSFER)).thenReturn(Optional.empty());
        when(referenceTypeMapper.toReferenceType("Transfer")).thenReturn(ReferenceType.TRANSFER);
        service.createTransaction(event);
        verify(repository).save(entity);
        when(repository.findByReferenceIdAndReferenceType(1L, ReferenceType.TRANSFER)).thenReturn(Optional.of(entity));
        service.createTransaction(event);
        verify(repository, times(1)).save(entity);
    }
    @Test void updatesExistingTransactionAndRejectsMissingOne() {
        TransactionEntity entity = new TransactionEntity();
        TransactionUpdated event = new TransactionUpdated("Transfer", 1L, Status.COMPLETE);
        when(referenceTypeMapper.toReferenceType("Transfer")).thenReturn(ReferenceType.TRANSFER);
        when(repository.findByReferenceIdAndReferenceType(1L, ReferenceType.TRANSFER)).thenReturn(Optional.of(entity));
        service.updateTransaction(event);
        assertThat(entity.getStatus()).isEqualTo(Status.COMPLETE);
        when(repository.findByReferenceIdAndReferenceType(2L, ReferenceType.TRANSFER)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.updateTransaction(new TransactionUpdated("Transfer", 2L, Status.FAILED))).isInstanceOf(TransactionNotFoundException.class);
    }
    @Test void returnsEmptyHistoryWhenUserHasNoAccounts() {
        var pageable = PageRequest.of(0, 10);
        when(accountClient.getUserAccounts(7L)).thenReturn(List.of());
        assertThat(service.getUserTransactionHistory(7L, pageable)).isEmpty();
        verifyNoInteractions(repository);
    }
}
