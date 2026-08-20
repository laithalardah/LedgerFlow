package com.example.paymentservice.entity;

import com.example.paymentservice.model.TransferModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Data
public class IdempotencyRecord {

    @Id
    private UUID key;

    private int statusCode;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private TransferModel responseBody;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private final LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

}
