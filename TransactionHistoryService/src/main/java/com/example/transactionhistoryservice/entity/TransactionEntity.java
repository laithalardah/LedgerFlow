package com.example.transactionhistoryservice.entity;

import com.example.transactionhistoryservice.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long debtorAccountNumber;

    private Long creditorAccountNumber;

    private BigDecimal amount;

    private LocalDateTime localDateTime = LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    private Status status = Status.Pending;
}
