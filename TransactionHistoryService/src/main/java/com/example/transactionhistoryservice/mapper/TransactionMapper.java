package com.example.transactionhistoryservice.mapper;

import com.example.transactionhistoryservice.entity.TransactionEntity;
import com.example.transactionhistoryservice.model.TransactionCreationModel;
import com.example.transactionhistoryservice.model.TransactionModel;
import com.example.transactionhistoryservice.resource.request.TransactionCreationResource;
import com.example.transactionhistoryservice.resource.response.TransactionResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionCreationModel toTransactionCreationModel(TransactionCreationResource transactionCreationResource);

    TransactionEntity toTransactionEntity(TransactionCreationModel transactionCreationModel);

    TransactionModel toTransactionModel(TransactionEntity transactionEntity);

    TransactionResource toTransactionResource(TransactionModel transactionModel);
}
