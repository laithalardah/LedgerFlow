package com.example.transactionhistoryservice.mapper;

import com.example.transactionhistoryservice.entity.TransactionEntity;
import com.example.transactionhistoryservice.model.TransactionModel;
import com.example.transactionhistoryservice.messaging.event.TransactionCreated;
import com.example.transactionhistoryservice.resource.TransactionResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "referenceType" ,ignore = true)
    @Mapping(target = "localDateTime" , source = "createdAt")
    TransactionEntity toTransactionEntity(TransactionCreated transactionCreated);

    TransactionModel toTransactionModel(TransactionEntity transactionEntity);

    TransactionResource toTransactionResource(TransactionModel transactionModel);
}
