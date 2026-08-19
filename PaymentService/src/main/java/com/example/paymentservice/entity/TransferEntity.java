package com.example.paymentservice.entity;


import com.example.paymentservice.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transfers")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long debtorAccountNumber;

    private Long creditorAccountNumber;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
}
