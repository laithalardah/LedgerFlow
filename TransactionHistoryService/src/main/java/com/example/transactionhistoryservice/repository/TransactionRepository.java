package com.example.transactionhistoryservice.repository;

import com.example.transactionhistoryservice.entity.TransactionEntity;
import com.example.transactionhistoryservice.enums.ReferenceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity , Long> {
    List<TransactionEntity> findByDebtorAccountNumberIn(List<Long> debtorAccountNumbers);

    Optional<TransactionEntity> findByReferenceIdAndReferenceType(Long id , ReferenceType referenceType);
}
