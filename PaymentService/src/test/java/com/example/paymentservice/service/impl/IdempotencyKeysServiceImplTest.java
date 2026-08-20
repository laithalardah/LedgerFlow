package com.example.paymentservice.service.impl;

import com.example.paymentservice.repository.IdempotentKeyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IdempotencyKeysServiceImplTest {
    @Mock private IdempotentKeyRepository repository;
    @InjectMocks private IdempotencyKeysServiceImpl service;
    @Test void deletesExpiredKeys() {
        service.idempotencyKeysCleanUp();
        verify(repository).deleteByExpiresAtBefore(any(LocalDateTime.class));
    }
}
