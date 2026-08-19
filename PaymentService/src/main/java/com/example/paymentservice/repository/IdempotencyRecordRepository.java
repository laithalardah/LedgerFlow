package com.example.paymentservice.repository;

import com.example.paymentservice.entity.IdempotencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IdempotencyRecordRepository extends JpaRepository<IdempotencyRecord, UUID> {

    void deleteByExpiresAtBefore(LocalDateTime localDateTime);
}
