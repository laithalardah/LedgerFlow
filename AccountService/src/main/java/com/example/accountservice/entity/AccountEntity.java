package com.example.accountservice.entity;


import com.example.accountservice.enums.AccountType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;


@Data
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    private BigDecimal balance = BigDecimal.ZERO;

    private AccountType accountType;

    private Currency currency;

    private Long userId;

    @Version
    private Long version;

}
