package com.example.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "idempotent_keys")
public class IdempotentKey {

    @Id
    private Long requestKey;

    private final LocalDateTime createdAt = LocalDateTime.now();

    public IdempotentKey(Long requestKey) {
        this.requestKey = requestKey;
    }
}
