package com.example.transactionhistoryservice.repository;

import com.example.transactionhistoryservice.entity.TransactionEntity;
import com.example.transactionhistoryservice.enums.ReferenceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity , Long> {

    Page<TransactionEntity> findByDebtorAccountNumberInOrCreditorAccountNumberIn(List<Long> debtor,
                                                                                 List<Long> creditor,
                                                                                 Pageable pageable);

    Optional<TransactionEntity> findByReferenceIdAndReferenceType(Long id , ReferenceType referenceType);
}
