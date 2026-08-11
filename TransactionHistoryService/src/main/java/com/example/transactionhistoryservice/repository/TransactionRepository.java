package com.example.transactionhistoryservice.repository;

import com.example.transactionhistoryservice.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity , Long> {
    List<TransactionEntity> findByDebtorAccountNumberIn(List<Long> debtorAccountNumbers);
}
