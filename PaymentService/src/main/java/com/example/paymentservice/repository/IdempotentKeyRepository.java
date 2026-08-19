package com.example.paymentservice.repository;

import com.example.paymentservice.entity.IdempotentKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface IdempotentKeyRepository extends JpaRepository<IdempotentKey , Long> {

    void deleteByExpiresAtBefore(LocalDateTime localDateTime);
}
