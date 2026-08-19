package com.example.paymentservice.service.impl;

import com.example.paymentservice.job.IdempotencyKeysCleanUpJob;
import com.example.paymentservice.repository.IdempotentKeyRepository;
import com.example.paymentservice.service.IdempotencyKeysService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class IdempotencyKeysServiceImpl implements IdempotencyKeysService {

    private final IdempotentKeyRepository idempotentKeyRepository;

    public IdempotencyKeysServiceImpl(IdempotentKeyRepository idempotentKeyRepository) {
        this.idempotentKeyRepository = idempotentKeyRepository;
    }

    @Override
    @Transactional
    public void idempotencyKeysCleanUp() {
        idempotentKeyRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
