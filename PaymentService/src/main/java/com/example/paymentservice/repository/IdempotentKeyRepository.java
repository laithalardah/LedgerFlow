package com.example.paymentservice.repository;

import com.example.paymentservice.entity.IdempotentKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotentKeyRepository extends JpaRepository<IdempotentKey , Long> {

}
